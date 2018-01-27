package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class GraficoInstituicaoDaoImplementation implements GraficoInstituicaoDao{

	@Override
	public List<Integer> questao1(int IDTurma) {
	List<Integer> respostas=new ArrayList<Integer>();
	try
	{
	Connection con = BD.getConnection();
	String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '1-%' and resposta = 'Sim' and IDTurma = ?";
	PreparedStatement stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDTurma);
	ResultSet rs=stmt.executeQuery();
	while(rs.next()){
	respostas.add(rs.getInt("resposta"));
	}
	
	sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '1-%' and resposta = 'Não' and IDTurma = ?";
	stmt=con.prepareStatement(sql);
	stmt.setInt(1, IDTurma);
	rs=stmt.executeQuery();
	while(rs.next()){
	respostas.add(rs.getInt("resposta"));
	}
	
	System.out.println("Tamanho:"+respostas.size());
	}
	
	catch(SQLException e){
	e.printStackTrace();
	}
		return respostas;
	}

	@Override
	public List<Integer> questao2(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '2-%' and resposta = 'Sim' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '2-%' and resposta = 'Não' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
		}


	@Override
	public List<Integer> questao3(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '3-%' and resposta = 'Sim' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '3-%' and resposta = 'Não' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
		}

	@Override
	public List<Integer> questao4(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '4-%' and resposta = 'Sim, todos oferecem' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '4-%' and resposta = 'Sim, a maioria oferece' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '4-%' and resposta = 'Sim; porém poucos professores oferecem' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '4-%' and resposta = 'Não, nenhum professor oferece' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
       }

	@Override
	public List<Integer> questao5(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '5-%' and resposta = 'Muito Bom' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '5-%' and resposta = 'Bom' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '5-%' and resposta = 'Regular' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '5-%' and resposta = 'Ruim' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '5-%' and resposta = 'Péssimo' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
       	
}

	@Override
	public List<Integer> questao6(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '6-%' and resposta = 'Sim' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '6-%' and resposta = 'Não' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return respostas;
	}

	@Override
	public List<Integer> questao7(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '7-%' and resposta = 'Sim, todos' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '7-%' and resposta = 'Sim, boa parte são de qualidade' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '7-%' and resposta = 'Sim, porém poucos são de qualidade' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '7-%' and resposta = 'Não, os equipamentos são de baixa qualidade' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		System.out.println("Tamanho:"+respostas.size());

		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return respostas;
	}
	

	@Override
	public List<Integer> questao8(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Não gosto da disciplina' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Não gosto do professor da matéria' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Não gosto do professor e da disciplina' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Julgo a disciplina pouco importante para a minha formação' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Trabalho e não tenho tempo para comparecer' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '8-%' and resposta = 'Problemas pessoais' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		catch(SQLException e){
		e.printStackTrace();
		}
		return respostas;
	}
	

	@Override
	public int questao9(String resposta, int IDTurma) {
	int resultado=0;
	try{
    Connection con = BD.getConnection();
    String sql = "Select Count (resposta) as 'resposta' from Questionario where questao like '9-%' and resposta = ? and IDTurma = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, resposta);
    stmt.setInt(2, IDTurma);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    resultado=rs.getInt("resposta");
    }
	}
	
	catch(SQLException e){
	e.printStackTrace();
	}
		return resultado;
	}
	


	@Override
	public List<Integer> questao10(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '10-%' and resposta = 'Plenamento Satisfeito' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '10-%' and resposta = 'Satisfeito' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '10-%' and resposta = 'Razoavelmente Satisfeito' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		

		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '10-%' and resposta = 'Insatisfeito' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		

		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '10-%' and resposta = 'Plenamente Insatisfeito' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
	
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
		}

	

	@Override
	public List<Integer> questao11(int IDTurma) {
		List<Integer> respostas=new ArrayList<Integer>();
		try
		{
		Connection con = BD.getConnection();
		String sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '11-%' and resposta = 'Sim' and IDTurma = ?";
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		
		sql =  "select Count (resposta) as 'resposta' from Questionario where questao like '11-%' and resposta = 'Não' and IDTurma = ?";
		stmt=con.prepareStatement(sql);
		stmt.setInt(1, IDTurma);
		rs=stmt.executeQuery();
		while(rs.next()){
		respostas.add(rs.getInt("resposta"));
		}
		System.out.println("Tamanho:"+respostas.size());

		}
		
		catch(SQLException e){
		e.printStackTrace();
		}
			return respostas;
		

	}

}
