package Dao;

import java.io.File;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import Pojo.Boletim;

public class BoletimDaoImplementation implements BoletimDao, Serializable {

	@Override
	public boolean inserir(Boletim boletim, int selecaoCurso) {
    DependenciaDao dependenciaDao=new DependenciaDaoImplementation();
	boolean efetuado=false;
	double numeroDoConceito=0;
	double contaReprovadoFalta=0;
    try{
    int validado=localizaTurmaBoletim(boletim);
        
    if(validado==1){
	Connection con = BD.getConnection();
	
	String localiza = "Select numeroDoConceito from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(localiza);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.setInt(2, boletim.getIDDaTurma());
	stmt.setInt(3, boletim.getIDDaDisciplina());
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    numeroDoConceito=rs.getDouble("numeroDoConceito");
    }
	
    if(numeroDoConceito!=2){


    String sql = "{call inserirDados(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
    CallableStatement cs =con.prepareCall(sql);
    cs.setInt(1, boletim.getNumeroDoConceito());
    cs.setString(2, boletim.getIdentificacaoAluno());
    cs.setInt(3, boletim.getIDDaTurma());
    cs.setInt(4, boletim.getIDDaDisciplina());
    cs.setInt(5, boletim.getModulo());
    cs.setDouble(6, boletim.getAulasDadasParciais());
    cs.setDouble(7, boletim.getAulasDadasFinais());
    System.out.println("Total de aulas dadas parciais cadastradas:"+boletim.getAulasDadasParciais());
    System.out.println("Total de aulas dadas finais cadastradas:"+boletim.getAulasDadasFinais());
    
    if(boletim.getConceitoParcial()==null){
    boletim.setConceitoParcial("");
    }
    if(boletim.getConceitoFinal()==null){
    boletim.setConceitoFinal("");
    }
    cs.setString(8, boletim.getConceitoParcial());
    cs.setString(9, boletim.getConceitoFinal());
    cs.setDouble(10, boletim.getFaltasParciais());
    
    System.out.println("Faltas parciais calculadas:"+boletim.getFaltasParciais());
    System.out.println("Faltas finais calculadas:"+boletim.getFaltasFinais());
    
    
    cs.setDouble(11, boletim.getFaltasFinais());
    
    cs.setDouble(12, boletim.getFrequenciaParcial());
    cs.setDouble(13, boletim.getFrequenciaFinal());
    cs.setString(14, boletim.getStatus());
    cs.setInt(15, selecaoCurso);
    cs.execute();
    cs.close();
  
    
    
    atualizaStatusCursoAluno(boletim);

    String contaRetidoFalta = "Select Count(statusFinal) as 'retidoFalta' from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por faltas' ";
    PreparedStatement contaRetido=con.prepareStatement(contaRetidoFalta);
    contaRetido.setString(1, boletim.getIdentificacaoAluno());
    contaRetido.setInt(2, boletim.getIDDaTurma());
    
    ResultSet localizaDado=contaRetido.executeQuery();
    
    if(localizaDado.next()){
    contaReprovadoFalta=localizaDado.getInt("retidoFalta");
    }
    
    
    System.out.println("Total de reprovados por falta:"+contaReprovadoFalta);
    
    if(boletim.getConceitoFinal().equalsIgnoreCase("I") && contaReprovadoFalta==0){
    dependenciaDao.inserir(boletim.getIdentificacaoAluno(), selecaoCurso, boletim.getIDDaDisciplina());
    incrementaStatus(boletim);
    }
    verificaRetencaoFalta(boletim);
    efetuado=true;  
    }
    
    System.out.println("Número do conceito:"+numeroDoConceito);
    
    if(boletim.getNumeroDoConceito()==1){
    atualizaNovoStatusAluno(boletim, "Nota parcial inserida");
    }
   if(boletim.getNumeroDoConceito()==2){
    atualizaNovoStatusAluno(boletim, "Nota final inserida");
    }
   verificaTotalDependencias(boletim, selecaoCurso);
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	return efetuado;	
	}
	
	
	
	public boolean incrementaStatus(Boletim boletim){
	boolean atualizado=false;
	try{
	Connection con = BD.getConnection();
	String sql = "UPDATE statusAluno set dependencias = dependencias + 1, totalDependencias = totalDependencias + 1 Where identificacao = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.executeUpdate();
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return atualizado;
	}
	
	
	public boolean verificaTotalDependencias(Boletim boletim, int selecaoCurso){
	
	System.out.println("Executando verificação de dependências");
		
	String statusAnterior="";
	int IDDaTurma = 0;
	int curso=0;
	String localizaStatusFalta="";
	List<Integer> listaID=new ArrayList<Integer>();
	int IDAnterior=0;
	
	boolean efetuado = false;
	try{
    Connection con = BD.getConnection();
	
    String localiza="Select boletim.statusFinal, curso.id as 'curso', boletim.IDDaTurma from Boletim " +
    "inner join turma "+
    "on boletim.IDDaTurma = Turma.IDTurma "+
    "inner join curso "+
    "on curso.id=Turma.curso "+
    "Where boletim.identificacaoAluno = ? and curso.id = ? and boletim.modulo = ? ";
    PreparedStatement stmt=con.prepareStatement(localiza);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, selecaoCurso);
    stmt.setInt(3, boletim.getModulo());
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    statusAnterior = rs.getString("statusFinal");
    IDDaTurma = rs.getInt("IDDaTurma");
    curso = rs.getInt("curso");
    }
    
    
    String localizaRetido="Select situacaoAtual  from StatusAluno where identificacao = ?";
    stmt=con.prepareStatement(localizaRetido);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    ResultSet promovidoLocalizado=stmt.executeQuery();
    if(promovidoLocalizado.next()){
    localizaStatusFalta=promovidoLocalizado.getString("situacaoAtual");
    }
    
    
    listaID=listaIDReprovacaoFalta(boletim);
    
    System.out.println("Tamanho:"+listaID.size());
    System.out.println("Atual:"+boletim.getIDDaTurma());
    
    for(int i=0;i<listaID.size();i++){
    if(listaID.get(i)==boletim.getIDDaTurma()){
    IDAnterior=1;
    }
    }
    
    System.out.println("ID Anterior:"+IDAnterior);
    System.out.println("Status Anterior Encontrado:"+statusAnterior);
    
    if(localizaStatusFalta.equalsIgnoreCase("A última ação foi o cadastro de uma nota final") && IDAnterior==1){
    
   
    if(statusAnterior.equalsIgnoreCase("Retido por faltas") && curso == selecaoCurso && IDDaTurma !=boletim.getIDDaTurma()){
   	
    System.out.println("Entrou no IF");
    	
    String sql = "{call verificaTotalDisciplinas(?,?,?,?,?,?)}";
    stmt=con.prepareCall(sql);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, boletim.getIDDaTurma());
    stmt.setInt(3, boletim.getModulo());
    stmt.setInt(4, selecaoCurso);
    stmt.setInt(5, boletim.getNumeroDoConceito());
    stmt.setString(6, statusAnterior);
    System.out.println("Status Anterior:"+statusAnterior);
    stmt.execute();
    stmt.close();
	efetuado=true;
    }
    }
    else
        if(localizaStatusFalta.equalsIgnoreCase("A última ação foi o cadastro de uma nota final") && IDAnterior==0){

    	if(statusAnterior.equalsIgnoreCase("Retido por faltas") && curso == selecaoCurso && IDDaTurma !=boletim.getIDDaTurma()){
		    System.out.println("Executando verificação de aprovação");
    	    String sql = "{call verificaTotalDisciplinasAprovacao(?,?,?,?,?,?)}";
    	    stmt=con.prepareCall(sql);
    	    stmt.setString(1, boletim.getIdentificacaoAluno());
    	    stmt.setInt(2, boletim.getIDDaTurma());
    	    stmt.setInt(3, boletim.getModulo());
    	    stmt.setInt(4, selecaoCurso);
    	    stmt.setInt(5, boletim.getNumeroDoConceito());
    	    stmt.setString(6, statusAnterior);
    	    System.out.println("Status Anterior:"+statusAnterior);
    	    stmt.execute();
    	    stmt.close();
    		efetuado=true;
    	    }
        }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return efetuado;
	}
	
	
	public List<Integer> listaIDReprovacaoFalta(Boletim boletim){
        List<Integer> listaID=new ArrayList<Integer>();
	    try{
        String localizaIDTurma = "Select distinct IDDaTurma from Boletim where identificacaoAluno = ? and statusFinal = 'Retido por faltas' order by IDDaTurma ";
	    Connection con = BD.getConnection();
	    PreparedStatement stmt=con.prepareStatement(localizaIDTurma);
	    stmt.setString(1, boletim.getIdentificacaoAluno());
	    ResultSet localizaID=stmt.executeQuery();
	    while(localizaID.next()){
	    int IDDaTurmaLocalizado = localizaID.getInt("IDDaTurma");
	    listaID.add(IDDaTurmaLocalizado);
	    }
	    }
	    catch(SQLException e){
	    e.printStackTrace();
	    }
	    return listaID;
	    } 

	@Override
	public Boletim consultar(Boletim boletim, int selecaoConceito) {
    Boletim b =new Boletim();
    try{
    Connection con = BD.getConnection();
    String sql = "Select IdentificacaoAluno, conceitoParcial, conceitoFinal, faltasParciais, " +
    "faltasFinais, frequenciaParcial, frequenciaFinal, statusFinal "+ 
    "from Boletim Where IdentificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, boletim.getIDDaTurma());
    stmt.setInt(3, boletim.getIDDaDisciplina());
    ResultSet rs=stmt.executeQuery();
    
    if(rs.next()){
    b.setIdentificacaoAluno(rs.getString("IdentificacaoAluno"));
    b.setConceitoParcial(rs.getString("conceitoParcial"));
    b.setConceitoFinal(rs.getString("conceitoFinal"));
    b.setFaltasParciais(rs.getDouble("faltasParciais"));
    b.setFaltasFinais(rs.getDouble("faltasFinais"));
    b.setFrequenciaParcial(rs.getDouble("frequenciaParcial"));
    b.setFrequenciaFinal(rs.getDouble("frequenciaFinal"));
    
    if(selecaoConceito==1){
    b.setStatus("");
    }
    else{
    b.setStatus(rs.getString("statusFinal"));
    }
    }
    }
    catch(SQLException e){
    e.printStackTrace();		
    }
		
	return b;
	}
	
	
	
	
	public boolean alterar(Boletim boletim){
	boolean efetuado=false;
	String localizado=null;
	try{
	int validado=localizaTurmaBoletim(boletim);
	if(validado==1){
	Connection con=BD.getConnection();
	if(boletim.getNumeroDoConceito()==1){
	 String sql = "Update Boletim set aulasDadasParciais = ?, conceitoParcial = ?, faltasParciais = ?, frequenciaParcial = ? Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
     PreparedStatement stmt=con.prepareStatement(sql);
     stmt.setDouble(1, boletim.getAulasDadasParciais());
	 stmt.setString(2, boletim.getConceitoParcial());
	 stmt.setDouble(3, boletim.getFaltasParciais());
	 stmt.setDouble(4, boletim.getFrequenciaParcial());
     stmt.setString(5, boletim.getIdentificacaoAluno());
	 stmt.setInt(6, boletim.getIDDaTurma());
	 stmt.setInt(7, boletim.getIDDaDisciplina());
	 stmt.executeUpdate();
	 efetuado=true;
	}
	else
	if(boletim.getNumeroDoConceito()==2){
	String localiza = "Select conceitoFinal from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
	PreparedStatement localizando=con.prepareStatement(localiza);
	localizando.setString(1, boletim.getIdentificacaoAluno());
	localizando.setInt(2, boletim.getIDDaTurma());
	localizando.setInt(3, boletim.getIDDaDisciplina());
	ResultSet rs=localizando.executeQuery();
	if(rs.next()){
    localizado = rs.getString("conceitoFinal");
	}
	
    String sql = "Update Boletim set aulasDadasFinais = ?, conceitoFinal = ?, faltasFinais = ?, frequenciaFinal = ?, statusFinal = ? Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setDouble(1, boletim.getAulasDadasFinais());
	stmt.setString(2, boletim.getConceitoFinal());
	stmt.setDouble(3, boletim.getFaltasFinais());
	stmt.setDouble(4, boletim.getFrequenciaFinal());

	stmt.setString(5, boletim.getStatus());
	stmt.setString(6, boletim.getIdentificacaoAluno());
	stmt.setInt(7, boletim.getIDDaTurma());
	stmt.setInt(8, boletim.getIDDaDisciplina());
	stmt.executeUpdate();
	atualizaStatus(boletim, localizado);
	efetuado=true;
    atualizaStatusCursoAluno(boletim);
    atualizaNovoStatusAluno(boletim, "Alterar");
	}
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return efetuado;
	}
	
	
	public boolean atualizaStatus(Boletim boletim, String localizado){
	boolean atualizado=false;
		try{
		Connection conexao = BD.getConnection();
		String calculaFrequenciaFinal = "{CALL calculaFrequenciaFinal (?,?,?,?,?,?,?)}";
		CallableStatement frequenciaFinal = conexao.prepareCall(calculaFrequenciaFinal);
		
		frequenciaFinal.setString(1, boletim.getIdentificacaoAluno());
		frequenciaFinal.setInt(2, boletim.getIDDaTurma());
		frequenciaFinal.setInt(3, boletim.getModulo());
		frequenciaFinal.setInt(4, boletim.getCurso());
		frequenciaFinal.setInt(5, boletim.getNumeroDoConceito());
		frequenciaFinal.setString(6, boletim.getConceitoFinal());
		frequenciaFinal.setString(7, "Alterar");
		frequenciaFinal.execute();
		frequenciaFinal.close();
		  atualizaStatusNota(boletim, boletim.getCurso(), localizado);
		  atualizado=true;
	

	}
	catch(SQLException e){
	e.printStackTrace();
	}
		return atualizado;
	}
	
	
	public boolean atualizaStatusNota(Boletim boletim, int IDCurso, String localizado){
    boolean atualizado=false;

    
    try{
    Connection con = BD.getConnection();
    String sql = "{call atualizaStatusAluno (?,?,?,?,?,?)}";
    PreparedStatement stmt=con.prepareCall(sql);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, IDCurso);
    stmt.setInt(3, boletim.getIDDaTurma());
    stmt.setString(4, boletim.getConceitoFinal());
    stmt.setInt(5, boletim.getIDDaDisciplina());
    stmt.setString(6, localizado);
  
    stmt.execute();
    stmt.close();
    atualizado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return atualizado;
	}

	@Override
	public boolean remover(Boletim boletim, int selecaoCurso, int conceito) {
	DependenciaDao dependenciaDao=new DependenciaDaoImplementation();
	boolean removido=false;
	String statusFinal = "";
	try{
	int validado=localizaTurmaBoletim(boletim);
	if(validado==1){

	Connection con = BD.getConnection();
	
	String localiza="Select statusFinal from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
	PreparedStatement stmt=con.prepareStatement(localiza);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.setInt(2, boletim.getIDDaTurma());
	stmt.setInt(3, boletim.getIDDaDisciplina());
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	statusFinal = rs.getString("statusFinal");
	}
	
	
	String sql = "Delete Boletim Where identificacaoAluno = ? and IDDaTurma = ? and IDDaDisciplina = ?";
	stmt=con.prepareStatement(sql);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.setInt(2, boletim.getIDDaTurma());
	stmt.setInt(3, boletim.getIDDaDisciplina());
    stmt.executeUpdate();
    removido=true;
    atualizaFrequenciaFinal(boletim, selecaoCurso, conceito, statusFinal);
    atualizaStatusCursoAluno(boletim);
    
    if(statusFinal.equalsIgnoreCase("Retido por nota")){
    dependenciaDao.remover(boletim.getIdentificacaoAluno(), boletim.getIDDaDisciplina());
    }
    
    verificaRetidoNotas(boletim, selecaoCurso);
    atualizaNovoStatusAluno(boletim, "Remover");
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return removido;
	}
	
	
	public double consultaFaltas(int IDCurso, int IDTurma, int IDDisciplina, String identificacaoAluno){
	double totalPresencas=0;
	double totalAulasDadas=0;
	double totalCargaHoraria=0;
	double totalFaltas=0;
	double totalAusenciasParciais;
	try{
	Connection con = BD.getConnection();
	String sql = "{Call presencaAluno_by_disciplina (?,?,?,?)}";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDDisciplina);
	stmt.setInt(2, IDTurma);
	stmt.setInt(3, IDCurso);
	stmt.setString(4, identificacaoAluno);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	totalPresencas=rs.getDouble("presencas");
	}
	
		
	totalAulasDadas = calculaTotalAulas(IDCurso, IDTurma,  IDDisciplina);
	totalCargaHoraria = localizaCargaHoraria(IDDisciplina);
	totalAusenciasParciais = localizaFaltasParciais (IDTurma, IDDisciplina, identificacaoAluno);

	if(totalCargaHoraria==50){
	totalPresencas=totalPresencas*2.5;
	}
	else
	if(totalCargaHoraria==100){
	totalPresencas=totalPresencas*5;
	}
    totalFaltas=totalAulasDadas-totalPresencas+totalAusenciasParciais;
	System.out.println("Total de ausencias:"+totalAusenciasParciais);
	}
	
	catch(SQLException e){
	e.printStackTrace();
	}
	return totalFaltas;
	}
	
	
	public double calculaTotalAulas(int IDCurso, int IDTurma, int IDDisciplina){
	double aulasCadastradasTotais=0;
	//double aulasCadastradasParciais=0;
	double cargaHoraria=0;
	double aulasDadas=0;
	try{
	Connection con = BD.getConnection();
	String localizaAulasDadasTotais = "Select Count(idDisciplina) as aulasDadas from diario where idDisciplina = ? and idTurma = ? and idCurso = ? -- and tipoChamada = 'Aula toda' ";
	PreparedStatement stmt=con.prepareStatement(localizaAulasDadasTotais);
	stmt.setInt(1, IDDisciplina);
	stmt.setInt(2, IDTurma);
	stmt.setInt(3, IDCurso);
    ResultSet localizaAulasTotais=stmt.executeQuery();
    if(localizaAulasTotais.next()){
    aulasCadastradasTotais=localizaAulasTotais.getDouble("aulasDadas");
    }
    
	/*String localizaAulasDadasParciais = "Select Count(idDisciplina) as aulasDadas from diario where idDisciplina = ? and idTurma = ? and idCurso = ? -- and tipoChamada = 'Metade da aula' ";
	stmt=con.prepareStatement(localizaAulasDadasParciais);
	stmt.setInt(1, IDDisciplina);
	stmt.setInt(2, IDTurma);
	stmt.setInt(3, IDCurso);
    ResultSet localizaAulasParciais=stmt.executeQuery();
    if(localizaAulasParciais.next()){
    aulasCadastradasParciais=localizaAulasParciais.getDouble("aulasDadas");
    }
    */
        
    String localizaCargaHoraria = "Select cargaHoraria from Disciplina Where IDDisciplina = ?";
    stmt=con.prepareStatement(localizaCargaHoraria);
    stmt.setInt(1, IDDisciplina);
    ResultSet localizaCargaDisciplina=stmt.executeQuery();
    if(localizaCargaDisciplina.next()){
    cargaHoraria = localizaCargaDisciplina.getDouble("cargaHoraria");
    }
   
    if(cargaHoraria==50){
    aulasDadas=aulasDadas+(aulasCadastradasTotais*2.5);
    //aulasDadas = aulasDadas + (aulasCadastradasParciais*1.25);
    }
    else
    if(cargaHoraria==100){
    aulasDadas=aulasDadas+(aulasCadastradasTotais*5);
    //aulasDadas = aulasDadas + (aulasCadastradasParciais*2.5);    
    }
    
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return aulasDadas;
	}
	
	
	public double localizaFaltasParciais(int IDTurma, int IDDisciplina, String identificacaoAluno){
	double atrasoLocalizado=0;
	try{
	Connection con = BD.getConnection();
	String sql = "Select SUM (valor) as 'atrasos' from atraso Where IDTurma = ? and IDDisciplina = ? and identificacao = ? ";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDTurma);
	stmt.setInt(2, IDDisciplina);
	stmt.setString(3, identificacaoAluno);
	ResultSet rs=stmt.executeQuery();
	if(rs.next()){
	atrasoLocalizado=rs.getDouble("atrasos");
    }
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return atrasoLocalizado;
	}
	
	
	public double localizaCargaHoraria(int IDDisciplina){
	double cargaHoraria=0;
	try{
		Connection con = BD.getConnection();
	    String localizaCargaHoraria = "Select cargaHoraria from Disciplina Where IDDisciplina = ?";
	    PreparedStatement stmt=con.prepareStatement(localizaCargaHoraria);
	    stmt.setInt(1, IDDisciplina);
	    ResultSet localizaCargaDisciplina=stmt.executeQuery();
	    if(localizaCargaDisciplina.next()){
	    cargaHoraria = localizaCargaDisciplina.getDouble("cargaHoraria");
	    }
		}
		catch(SQLException e){
		e.printStackTrace();
	}
	return cargaHoraria;
	}
	
	
	@Override
	public boolean atualizarFrequenciaParcial(String identificacaoAluno,
			int IDDaTurma, int modulo, int curso, int conceito) {
    boolean efetuado=false;
	try{
    Connection con = BD.getConnection();
    String sql = "{call calculaFrequenciaParcial(?,?,?,?,?)}";
    CallableStatement cs=con.prepareCall(sql);
    cs.setString(1, identificacaoAluno);
    cs.setInt(2, IDDaTurma);
    cs.setInt(3, modulo);
    cs.setInt(4, curso);
    cs.setInt(5, conceito);
    cs.execute();
    cs.close();
    efetuado=true;
    }
    catch(SQLException e){
    e.printStackTrace();
    }
	return efetuado;
	}
	

	@Override
	public boolean atualizaFrequenciaFinal(Boletim boletim, int selecaoCurso, int conceito, String statusFinal) {
    boolean atualizada=false;
    int contadorFalta=0;
    int contadorNota=0;
    
	try{
    Connection con = BD.getConnection();
    String sql = "Update Boletim set frequenciaGeralParcial = null, frequenciaGeralFinal = null " +
    "Where identificacaoAluno = ? and IDDaTurma = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, boletim.getIDDaTurma());
    stmt.execute();
    atualizada=true;
    
    try{
    	
    String contaReprovadoFalta = "Select Count(statusFinal) as 'retidoPorFalta' from boletim Where statusFinal = 'Retido por faltas' and identificacaoAluno = ? and IDDaTurma = ?";
    PreparedStatement contaRetidosFalta=con.prepareStatement(contaReprovadoFalta);
    contaRetidosFalta.setString(1, boletim.getIdentificacaoAluno());
    contaRetidosFalta.setInt(2, boletim.getIDDaTurma());
    ResultSet reprovadosFalta = contaRetidosFalta.executeQuery();
    while(reprovadosFalta.next()){
    contadorFalta = reprovadosFalta.getInt("retidoPorFalta");
    contadorFalta=contadorFalta+1;
    }

  
    
    String verifica = "Select statusFinal from Boletim Where identificacaoAluno = ? and IDDaTurma = ?";
    PreparedStatement result=con.prepareStatement(verifica);
    result.setString(1, boletim.getIdentificacaoAluno());
    result.setInt(2, boletim.getIDDaTurma());
    ResultSet rs=result.executeQuery();
    while(rs.next()){
    String status = rs.getString("statusFinal");
    if(status.equalsIgnoreCase("Retido por faltas")){
    atualizaStatusFinal(boletim.getIdentificacaoAluno(), boletim.getIDDaTurma(), boletim.getModulo(), selecaoCurso, conceito);
    }
    } 
    
    
    String contaReprovadoNota = "Select Count(statusFinal) as 'retidoPorNota' from boletim Where statusFinal = 'Retido por nota' and identificacaoAluno = ? and IDDaTurma = ?";
    PreparedStatement contaRetidoNota = con.prepareStatement(contaReprovadoNota);
    contaRetidoNota.setString(1, boletim.getIdentificacaoAluno());
    contaRetidoNota.setInt(2, boletim.getIDDaTurma());
    ResultSet reprovadoNota=contaRetidoNota.executeQuery();
    if(reprovadoNota.next()){
    contadorNota=reprovadoNota.getInt("retidoPorNota");
    }
    

    
    if(statusFinal.equalsIgnoreCase("Retido por faltas")){
    atualizaNumeroDeDependenciasFalta(contadorFalta, contadorNota, boletim.getIdentificacaoAluno(), boletim.getIDDaTurma(), statusFinal);
    stmt.close();
    }
    else
    if(statusFinal.equalsIgnoreCase("Retido por nota")){
     atualizaNumeroDeDependenciasNota(boletim.getIdentificacaoAluno(), boletim.getIDDaTurma());
    }
    }
    
    
    catch(SQLException e){
    e.printStackTrace();
    }
	}
    catch(SQLException e){
    e.printStackTrace();
    }
    
		return atualizada;
	}
	
	
	public boolean atualizaNumeroDeDependenciasFalta(int contadorFalta, int contadorNota, String identificacaoAluno, int IDDaTurma,
	String statusFinal){
	boolean atualizado=false;
	try{
	Connection con = BD.getConnection();
	  
	int subtraiRetidoNota = subtraiRetidoNota(identificacaoAluno, IDDaTurma);
	
	
	if(statusFinal.contains("Retido")){
	String subtraiRetidos ="UPDATE StatusAluno set dependencias = dependencias - ?, totalDependencias = totalDependencias - ? Where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(subtraiRetidos);
    stmt.setInt(1, subtraiRetidoNota);
    stmt.setInt(2, subtraiRetidoNota);
    stmt.setString(3, identificacaoAluno);
    stmt.execute();
     	
	String retidoFalta ="UPDATE StatusAluno set dependencias = dependencias - ?, totalDependencias = totalDependencias - ? Where identificacao = ?";
    stmt=con.prepareStatement(retidoFalta);
	stmt.setInt(1, contadorFalta);
    stmt.setInt(2, contadorFalta);
    stmt.setString(3, identificacaoAluno);
    stmt.execute();
	
    String retidoNota = "UPDATE StatusAluno set dependencias = dependencias + ?, totalDependencias = totalDependencias + ? Where identificacao = ?";
    contadorNota=contadorNota+contadorNota;
    stmt=con.prepareStatement(retidoNota);
    stmt.setInt(1, contadorNota);
    stmt.setInt(2, contadorNota);
    stmt.setString(3, identificacaoAluno);
	stmt.execute();
	stmt.close();
	atualizado=true;
	
	
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	
	return atualizado;
	}
	
	
	public boolean atualizaNumeroDeDependenciasNota(String identificacaoAluno, int IDDaTurma){
	boolean atualizado=false;
	try{
	Connection con = BD.getConnection();
	  	
	String subtraiRetidos ="UPDATE StatusAluno set dependencias = dependencias - 1, totalDependencias = totalDependencias - 1 Where identificacao = ?";
    PreparedStatement stmt=con.prepareStatement(subtraiRetidos);
    stmt.setString(1, identificacaoAluno);
    stmt.execute();
   
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	
	return atualizado;
	}
	

	@Override
	public boolean atualizaStatusFinal(String identificacaoAluno, int IDDaTurma,
			int modulo, int curso, int conceito) {
    boolean atualizado=false;
    try{
    Connection con = BD.getConnection();
    String atualizaAprovados = "UPDATE Boletim set statusFinal = 'Promovido' Where identificacaoAluno = ? and IDDaTurma = ? and " +
    "conceitoFinal != 'I' ";
    PreparedStatement aprovados = con.prepareStatement(atualizaAprovados);
    aprovados.setString(1, identificacaoAluno);
    aprovados.setInt(2, IDDaTurma);
    aprovados.execute();
    
    String atualizaReprovados = "UPDATE Boletim set statusFinal = 'Retido por nota' Where identificacaoAluno = ? and IDDaTurma = ? and " +
    "conceitoFinal = 'I' ";
    PreparedStatement reprovados = con.prepareStatement(atualizaReprovados);
    reprovados.setString(1, identificacaoAluno);
    reprovados.setInt(2, IDDaTurma);
    reprovados.execute();
    
    con.close();
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return atualizado;
	}
	
	
     public boolean verificaRetidoNotas(Boletim boletim, int IDCurso){
     boolean efetuado=false;
     DependenciaDao dependenciaDao=new DependenciaDaoImplementation(); 
     try{
     Connection con = BD.getConnection();
     String sql = "Select IDDaDisciplina from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por nota' ";
     PreparedStatement stmt=con.prepareStatement(sql);
     stmt.setString(1, boletim.getIdentificacaoAluno());
     stmt.setInt(2, boletim.getIDDaTurma());
     ResultSet rs=stmt.executeQuery();
     while(rs.next()){
     int IDDaDisciplina = rs.getInt("IDDaDisciplina");
     dependenciaDao.inserir(boletim.getIdentificacaoAluno(), IDCurso, IDDaDisciplina);
     }
     efetuado=true;
     }
     catch(SQLException e){
     e.printStackTrace();
     }
     return efetuado;
     }
     

	

	public JasperPrint gerarPDF(int selecaoTurma, int selecaoDisciplina) throws JRException{
        JasperPrint rel = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
		String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

		try {
			Connection con = BD.getConnection();
			Map map = new HashMap();
			map.put("IDTurma",selecaoTurma);
			map.put("IDDisciplina", selecaoDisciplina);
			String arquivoJasper = caminho+File.separator+"relatorioGeralMencoesDisciplina.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, map, con);
		    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
	        response.setContentType("Relatorio/pdf");
	        response.addHeader("Content-disposition", "attachment; filename=Notas da Disciplina : "+selecaoDisciplina+" .pdf");
	           // Exporta o relatório
	            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
	            // Salva o estado da aplicação no contexto do JSF
	            fc.getApplication().getStateManager().saveView(fc);
	            // Fecha o stream do response
	            fc.responseComplete();
	        } catch (Exception e) {
	        }
		
		return rel;
	}

	
	@Override
	public JasperPrint gerarBoletimParcial(String identificacao, int selecaoTurma,
			int selecaoModulo) throws JRException {
		   JasperPrint rel = null;
  		
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
			String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

			try {
				Connection con = BD.getConnection();
				Map map = new HashMap();
				map.put("identificacaoAluno",identificacao);
				map.put("IDDaTurma", selecaoTurma);
				map.put("modulo", selecaoModulo);
				String arquivoJasper = caminho+File.separator+"relatorioMencoesParciaisAluno.jasper";
				rel = JasperFillManager.fillReport(arquivoJasper, map, con);
			    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
		        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		        response.setContentType("Relatorio/pdf");
		        response.addHeader("Content-disposition", "attachment; filename=Menções Parciais do estudante : "+identificacao+" .pdf");
		 
		           // Exporta o relatório
		            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
		            // Salva o estado da aplicação no contexto do JSF
		            fc.getApplication().getStateManager().saveView(fc);
		            // Fecha o stream do response
		            fc.responseComplete();
		        } catch (Exception e) {
		        }
		   
			
			return rel;
	
	}
	
	
	@Override
	public JasperPrint gerarBoletimFinal(String identificacao, int selecaoTurma,
			int selecaoModulo) throws JRException {

		   JasperPrint rel = null;
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
			String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

			try {
				Connection con = BD.getConnection();
				Map map = new HashMap();
				map.put("identificacaoAluno",identificacao);
				map.put("IDDaTurma", selecaoTurma);
				map.put("modulo", selecaoModulo);
				String arquivoJasper = caminho+File.separator+"relatorioMencoesFinaisAluno.jasper";
				rel = JasperFillManager.fillReport(arquivoJasper, map, con);
			    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
		        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		        response.setContentType("Relatorio/pdf");
		        response.addHeader("Content-disposition", "attachment; filename=Menções Finais do estudante : "+identificacao+" .pdf");
		 
		           // Exporta o relatório
		            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
		            // Salva o estado da aplicação no contexto do JSF
		            fc.getApplication().getStateManager().saveView(fc);
		            // Fecha o stream do response
		            fc.responseComplete();
		        } catch (Exception e) {
		        }
			
			return rel;
	
	}

	@Override
	public JasperPrint acompanhamentoGeralDoEstudante(String identificacao, int selecaoTurma) throws JRException {

		   JasperPrint rel = null;
			FacesContext fc = FacesContext.getCurrentInstance();
			ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
			String caminho = sc.getRealPath(File.separator+"/WEB-INF/lib/Relatorio");

			try {
				Connection con = BD.getConnection();
				Map map = new HashMap();
				map.put("identificacaoAluno",identificacao);
				map.put("IDDaTurma", selecaoTurma);
				String arquivoJasper = caminho+File.separator+"acompanhamentoFinalDoAluno.jasper";
				rel = JasperFillManager.fillReport(arquivoJasper, map, con);
			    //	JasperViewer.viewReport(rel, false); abre o relatório do IREPORT
		        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		        response.setContentType("Relatorio/pdf");
		        response.addHeader("Content-disposition", "attachment; filename=Acompanhamento geral do estudante : "+identificacao+" .pdf");
		 
		           // Exporta o relatório
		            JasperExportManager.exportReportToPdfStream(rel, response.getOutputStream());
		            // Salva o estado da aplicação no contexto do JSF
		            fc.getApplication().getStateManager().saveView(fc);
		            // Fecha o stream do response
		            fc.responseComplete();
		        } catch (Exception e) {
		        }
			
			return rel;
	
	}


	
	
	   public boolean verificaMencaoParcial(String identificacaoAluno, int selecaoTurma, int selecaoModulo, int IDCurso){
	   boolean localizada=false;
	   double quantidadeInformada=0;
	   double quantidadeTotal=0;
	   try{
	   Connection con = BD.getConnection();
	   String disciplinasInformadas = "Select Count(IDDisciplina) as 'disciplinasInformadas' "+
"from disciplina inner join boletim on disciplina.IDDisciplina = boletim.IDDaDisciplina "+
"where IdentificacaoAluno = ? and IDDaTurma = ? and conceitoParcial is not null "+ 
"and conceitoParcial != '' ";
	   
	   
	   PreparedStatement informadas=con.prepareStatement(disciplinasInformadas);
	   informadas.setString(1, identificacaoAluno);
	   informadas.setInt(2, selecaoTurma);
	   ResultSet rs=informadas.executeQuery();
	   if(rs.next()){
	   quantidadeInformada=rs.getDouble("disciplinasInformadas");
	   }
	   
	   String disciplinasLocalizadas = "Select Count(IDDisciplina) as 'disciplinasLocalizadas' from disciplina inner join curso on disciplina.curso = curso.id  Where modulo = ? and curso.id = ?";
       PreparedStatement totais=con.prepareStatement(disciplinasLocalizadas);
       totais.setInt(1, selecaoModulo);
       totais.setInt(2, IDCurso);
       

       ResultSet rsTotais=totais.executeQuery();
       if(rsTotais.next()){
       quantidadeTotal=rsTotais.getDouble("disciplinasLocalizadas");
       }
	   
	   if(quantidadeInformada==quantidadeTotal){
	   localizada=true;
	   }
	   else{
	   localizada=false;
	   }
	   }
	   catch(SQLException e){
	   e.printStackTrace();
	   }
	   return localizada;
	   }
		
   

		
	   public boolean verificaMencaoFinal(String identificacaoAluno, int selecaoTurma, int selecaoModulo, int IDCurso){
	   boolean localizada=false;
	   double quantidadeInformada=0;
	   double quantidadeTotal=0;
	   try{
	   Connection con = BD.getConnection();
	   String disciplinasInformadas = "Select Count(IDDisciplina) as 'disciplinasInformadas' "+
"from disciplina inner join boletim on disciplina.IDDisciplina = boletim.IDDaDisciplina "+
"where IdentificacaoAluno = ? and IDDaTurma = ? and conceitoParcial is not null "+ 
"and conceitoParcial != '' and conceitoFinal is not null and conceitoFinal != '' ";
	   
	   PreparedStatement informadas=con.prepareStatement(disciplinasInformadas);
	   informadas.setString(1, identificacaoAluno);
	   informadas.setInt(2, selecaoTurma);
	   ResultSet rs=informadas.executeQuery();
	   if(rs.next()){
	   quantidadeInformada=rs.getDouble("disciplinasInformadas");
	   }
	   
	   String disciplinasLocalizadas = "Select Count(IDDisciplina) as 'disciplinasLocalizadas' from disciplina inner join curso on disciplina.curso = curso.id  Where modulo = ? and curso.id = ?";
       PreparedStatement totais=con.prepareStatement(disciplinasLocalizadas);
       totais.setInt(1, selecaoModulo);
       totais.setInt(2, IDCurso);
       ResultSet rsTotais=totais.executeQuery();
       if(rsTotais.next()){
       quantidadeTotal=rsTotais.getDouble("disciplinasLocalizadas");
       }
	   
	   if(quantidadeInformada==quantidadeTotal){
	   localizada=true;
	   }
	   else{
	   localizada=false;
	   }
	   }
	   catch(SQLException e){
	   e.printStackTrace();
	   }
	   return localizada;
	   }
		
	   
	   public int subtraiRetidoNota(String identificacaoAluno, int IDDaTurma){
	   int reprovacoes=0;
	   try{
	   Connection con = BD.getConnection();
	   String sql = "Select Count (statusFinal) as 'retidoPorNota' from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por nota' ";
	   PreparedStatement stmt=con.prepareStatement(sql);
	   stmt.setString(1, identificacaoAluno);
	   stmt.setInt(2, IDDaTurma);
	   ResultSet rs=stmt.executeQuery();
	   if(rs.next()){
	   reprovacoes=rs.getInt("retidoPorNota");
	   }
	   }
	   catch(SQLException e){
	   e.printStackTrace();
	   }
	   return reprovacoes;
	   }
	   
	   
	   public boolean atualizaStatusCursoAluno(Boletim boletim){ 
	   boolean atualizado=false;
	   int quantidadeFaltasLocalizada=0;
	   int quantidadeNotasLocalizada=0;
	   try{
	   Connection con = BD.getConnection();
	   String statusRetencaoFaltas = "Select Count(statusFinal) as 'retidosFalta' from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por faltas' ";
	   PreparedStatement stmt=con.prepareStatement(statusRetencaoFaltas);
	   stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.setInt(2, boletim.getIDDaTurma());
	     
	   ResultSet retidoFaltas=stmt.executeQuery();
	   if(retidoFaltas.next()){
	   quantidadeFaltasLocalizada = retidoFaltas.getInt("retidosFalta");
	   }
	   
	   String statusRetencaoNota = "Select Count(statusFinal) as 'retidoNota' from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por nota' ";
	   stmt=con.prepareStatement(statusRetencaoNota);
	   stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.setInt(2, boletim.getIDDaTurma());	   
	   
	   
	   ResultSet retidoNota=stmt.executeQuery();
	   if(retidoNota.next()){
	   quantidadeNotasLocalizada = retidoNota.getInt("retidoNota");
	   }
	   
	   
	   
	   if(quantidadeFaltasLocalizada>=3){
	   String atualizaRetidoFalta="UPDATE statusAluno set situacaoCurso = 'Retido por faltas' Where identificacao = ?";
	   stmt=con.prepareStatement(atualizaRetidoFalta);
	   stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.execute();
	   }
	   
	   
	   
	   if(quantidadeNotasLocalizada>=3){
	   String atualizaRetidoFalta="UPDATE statusAluno set situacaoCurso = 'Retido por nota' Where identificacao = ?";
	   stmt=con.prepareStatement(atualizaRetidoFalta);
	   stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.execute();
	   }
	   
	   
	   if(quantidadeNotasLocalizada>0 && quantidadeNotasLocalizada <=2){
	   String atualizaRetidoNota="UPDATE statusAluno set situacaoCurso = 'Promovido com progressão parcial' Where identificacao = ?";
	   stmt=con.prepareStatement(atualizaRetidoNota);
	   stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.execute();
	   }
	   
	   else
	   if(quantidadeFaltasLocalizada==0 && quantidadeNotasLocalizada==0){
       String atualizaAprovado="UPDATE statusAluno set situacaoCurso = 'Promovido' Where identificacao = ?";
       stmt=con.prepareStatement(atualizaAprovado);
       stmt.setString(1, boletim.getIdentificacaoAluno());
	   stmt.execute();
	   }
       stmt.close();
	   atualizado=true;
	   }
	   catch(SQLException e){
	   e.printStackTrace();
	   }
	   
	  
	   
	   return atualizado;
	   
	   }


	@Override
	public boolean verificaRetencaoFalta(Boletim boletim) {
    boolean efetuado=false;
	DependenciaDao dependenciaDao = new DependenciaDaoImplementation();
	try{
    Connection con  = BD.getConnection();
    String sql = "Select IDDaDisciplina from Boletim Where identificacaoAluno = ? and IDDaTurma = ? and statusFinal = 'Retido por faltas' ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, boletim.getIdentificacaoAluno());
    stmt.setInt(2, boletim.getIDDaTurma());
    ResultSet rs=stmt.executeQuery();
    while(rs.next()){
    int IDDaDisciplina=rs.getInt("IDDaDisciplina");
    dependenciaDao.remover(boletim.getIdentificacaoAluno(), IDDaDisciplina);
    }
    efetuado=true;
    }
	catch(SQLException e){
	e.printStackTrace();
	}
		return efetuado;
	}
	
	
	public boolean atualizaNovoStatusAluno(Boletim boletim, String requisicao){
	System.out.println("Atualizando novo status aluno");
	boolean efetuado=false;
	try{
	Connection con = BD.getConnection();
	String sql="";
	if(requisicao.equalsIgnoreCase("Nota parcial inserida")){
	sql = "UPDATE statusAluno set situacaoAtual = 'A última ação foi o cadastro de uma nota parcial' where identificacao = ?";
	System.out.println("Atualizando nota parcial");
	}
	else
	if(requisicao.equalsIgnoreCase("Nota final inserida")){
	sql = "UPDATE statusAluno set situacaoAtual = 'A última ação foi o cadastro de uma nota final' where identificacao = ?";
	System.out.println("Atualizando nota final");
	}
	else
	if(requisicao.equalsIgnoreCase("Alterar")){
	sql = "UPDATE statusAluno set situacaoAtual = 'A última ação foi a alteração de uma nota' where identificacao = ?";
    }
	else
	if(requisicao.equalsIgnoreCase("Remover")){
	sql = "UPDATE statusAluno set situacaoAtual = 'A última ação foi a remoção de uma nota' where identificacao = ?";
	}
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.executeUpdate();
	efetuado=true;
	
	if(requisicao.equalsIgnoreCase("Remover")){
    Connection atualiza=BD.getConnection();
	String atualizaObservacao = "UPDATE Boletim set observacao = '' Where identificacaoAluno = ? and IDDaTurma < ?";
	PreparedStatement at=atualiza.prepareStatement(atualizaObservacao);
	at.setString(1, boletim.getIdentificacaoAluno());
	at.setInt(2, boletim.getIDDaTurma());
	at.executeUpdate();
	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return efetuado;
	}
   
	
	
	public int localizaTurmaBoletim(Boletim boletim){
	int turmaLocalizada=0;
	int validado=0;
	int verificaTurma=0;
	try{
	Connection con = BD.getConnection();
	String sql = "Select distinct IDDaTurma from Boletim Where identificacaoAluno = ? and modulo = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setString(1, boletim.getIdentificacaoAluno());
	stmt.setInt(2, boletim.getModulo());
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	turmaLocalizada = rs.getInt("IDDaTurma");
	
	if(boletim.getIDDaTurma()==turmaLocalizada){
	validado=1;
    }
	else{
	validado=0;
	}
	}
	
	if(validado==0){
	System.out.println("Executando verificação");
	System.out.println(turmaLocalizada);
	System.out.println(boletim.getIDDaTurma());
	System.out.println(boletim.getModulo());
	System.out.println(boletim.getIdentificacaoAluno());
	String localizaIdentificacaoNula = "Select AlunoTurma.IDDaTurma, Turma.modulo from Turma " +
	"inner join AlunoTurma "+
	"on AlunoTurma.IDDaTurma = Turma.IDTurma "+
	"Where IDDaTurma = ? and IDDaTurma > ? and modulo = ? and AlunoTurma.identificacaoAluno = ?";
	stmt=con.prepareStatement(localizaIdentificacaoNula);
	stmt.setInt(1, boletim.getIDDaTurma());
	stmt.setInt(2, turmaLocalizada);
	stmt.setInt(3, boletim.getModulo());
	stmt.setString(4, boletim.getIdentificacaoAluno());
	
	ResultSet nulo=stmt.executeQuery();
	while(nulo.next()){
	verificaTurma=nulo.getInt("IDDaTurma");
	}
	
	System.out.println("Turma localizada:"+turmaLocalizada);
	
	if(verificaTurma>=1){
	validado=1;
	}
	System.out.println("Validado:"+validado);

	}
	}
	catch(SQLException e){
	e.printStackTrace();
	}
	return validado;
	}
	
	
	
}
	