package Dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojo.Turma;

public class EstatisticaDaoImplementation  implements EstatisticaDao, Serializable{

	@Override
	public List<Integer> estatisticaTurmaParcial(int selecaoTurma) {
		return null;
		// TODO Auto-generated method stub

	}


	@Override
	public List<Integer> estatisticaTurmaFinal(int selecaoTurma) {
		List<Integer> dadosLocalizados=new ArrayList<Integer>();
		int contador=0;
		int retencaoNotas=0;
		int aprovacao=0;
		try{
			Connection con = BD.getConnection();
			String reprovadoFaltas = "Select Count (distinct IdentificacaoAluno) as 'retidoFaltas' from Boletim Where IDDaTurma = ? and statusFinal = 'Retido por faltas' ";
			PreparedStatement stmt=con.prepareStatement(reprovadoFaltas);
			stmt.setInt(1, selecaoTurma);
			ResultSet retidoFaltas=stmt.executeQuery();
			if(retidoFaltas.next()){
				int estatistica;
				estatistica = retidoFaltas.getInt("retidoFaltas");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;
			String reprovadoNotas = "Select Count (distinct statusFinal) as 'retidoNotas' from Boletim Where IDDaTurma = ? and statusFinal = 'Retido por nota' "+
					"Group by identificacaoAluno "+ 
					"having COUNT (*) >=3";

			stmt=con.prepareStatement(reprovadoNotas);
			stmt.setInt(1, selecaoTurma);
			ResultSet retidoNotas=stmt.executeQuery();
			while(retidoNotas.next()){
				int estatistica;
				estatistica = retidoNotas.getInt("retidoNotas");
				contador=contador+1;
			}

			if(contador>=0){
				dadosLocalizados.add(contador);     
				retencaoNotas = retencaoNotas + contador;
			}
			else{
				dadosLocalizados.add(0);
			}

			contador = 0;


			String promovido="Select Count (distinct IdentificacaoAluno) as 'promovido' from Boletim Where IDDaTurma = ? and statusFinal = 'Promovido' ";
			stmt=con.prepareStatement(promovido);
			stmt.setInt(1, selecaoTurma);
			ResultSet aprovado=stmt.executeQuery();
			if(aprovado.next()){
				int estatistica;
				estatistica = aprovado.getInt("promovido");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);
				aprovacao=aprovacao+contador;
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;

			String promovidoComProgressaoParcial ="Select  Count (distinct identificacaoAluno) as 'promovidoComDependencias' from Boletim Where IDDaTurma = ? and statusFinal = 'Retido por nota' "+
					"Group by identificacaoAluno  "+ 
					"having COUNT (*) <=2";
			stmt=con.prepareStatement(promovidoComProgressaoParcial);
			stmt.setInt(1, selecaoTurma);
			ResultSet promovidoComProgressoesParciais=stmt.executeQuery();
			while(promovidoComProgressoesParciais.next())
			{
				int estatistica;
				estatistica = promovidoComProgressoesParciais.getInt("promovidoComDependencias");
				contador=contador+1;
				System.out.println("Total de dependências parciais:"+contador);
			}

			if(contador>0){
				dadosLocalizados.add(contador); 
				retencaoNotas = retencaoNotas+contador;
			}
			else{
				dadosLocalizados.add(0);
			}

			aprovacao = aprovacao-retencaoNotas;
			if(aprovacao>=0){
				dadosLocalizados.set(2, aprovacao);
			}
			else{
				dadosLocalizados.set(2, 0);
			}
		}

		catch(SQLException e){
			e.printStackTrace();
		}

		return dadosLocalizados;

	}


	public List<Integer> estatisticaAreaConcentracao(int selecaoArea, int IDTurma) {
		List<Integer> listaAreas=new ArrayList<Integer>();
		List<Integer> dadosLocalizados=new ArrayList<Integer>();
		int contador=0;
		int retencaoNotas=0;
		int aprovacao=0;
		try{
			Connection con = BD.getConnection();


			String promovido="Select Count (distinct IdentificacaoAluno) as 'promovido' from Boletim "+
					"inner join Disciplina "+
					"on Disciplina.IDDisciplina = Boletim.IDDaDisciplina "+
					"Where Disciplina.areaConcentracao = ? and  IDDaTurma = ? and statusFinal = 'Promovido' ";
			PreparedStatement stmt=con.prepareStatement(promovido);
			stmt.setInt(1, selecaoArea);
			stmt.setInt(2, IDTurma);
			ResultSet aprovado=stmt.executeQuery();
			if(aprovado.next()){
				int estatistica;
				estatistica = aprovado.getInt("promovido");
				contador=estatistica;
			}

			if(contador>0){

				dadosLocalizados.add(contador);
				aprovacao=aprovacao+contador;
			}
			else{
				dadosLocalizados.add(0);
			}
			contador = 0;


			String retidoPorNota ="Select  Count (distinct identificacaoAluno) as 'retidoPorNota' from Boletim "+
					"inner join disciplina "+
					"on disciplina.IDDisciplina = Boletim.IDDaDisciplina "+
					"Where Disciplina.areaConcentracao = ? and IDDaTurma = ? and statusFinal = 'Retido por nota' ";

			stmt=con.prepareStatement(retidoPorNota);
			stmt.setInt(1, selecaoArea);
			stmt.setInt(2, IDTurma);
			ResultSet retidoNota=stmt.executeQuery();
			while(retidoNota.next())
			{
				int estatistica;
				estatistica = retidoNota.getInt("retidoPorNota");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador); 
				retencaoNotas = retencaoNotas+contador;
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;

			String reprovadoFaltas = "Select Count (distinct IdentificacaoAluno) as 'retidoFaltas' from Boletim "+
					"inner join Disciplina "+
					"on Disciplina.IDDisciplina = Boletim.IDDaDisciplina "+
					"Where Disciplina.areaConcentracao = ? and IDDaTurma = ? and statusFinal = 'Retido por faltas' ";	    
			stmt=con.prepareStatement(reprovadoFaltas);
			stmt.setInt(1, selecaoArea);
			stmt.setInt(2, IDTurma);
			ResultSet retidoFaltas=stmt.executeQuery();
			if(retidoFaltas.next()){
				int estatistica;
				estatistica = retidoFaltas.getInt("retidoFaltas");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);
			}
			else{
				dadosLocalizados.add(0);
			}
		}	   

		catch(SQLException e){
			e.printStackTrace();
		}

		return dadosLocalizados;

	}


	public List<Integer> estatisticaDisciplinaParcial(int selecaoTurma, int selecaoDisciplina){
		List<Integer> listaNotas=new ArrayList<Integer>();
		try{
			Connection con = BD.getConnection();
			String notaI="Select Count (distinct IdentificacaoAluno) as 'notaParcialI' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ?  and conceitoParcial = 'I' ";
			PreparedStatement stmt=con.prepareStatement(notaI);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaParcialI=stmt.executeQuery();
			if(notaParcialI.next()){
				listaNotas.add(notaParcialI.getInt("notaParcialI"));
			}

			String notaR="Select Count (distinct IdentificacaoAluno) as 'notaParcialR' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoParcial = 'R' ";
			stmt=con.prepareStatement(notaR);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaParcialR=stmt.executeQuery();
			if(notaParcialR.next()){
				listaNotas.add(notaParcialR.getInt("notaParcialR"));
			}

			String notaB="Select Count (distinct IdentificacaoAluno) as 'notaParcialB' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoParcial = 'B' ";
			stmt=con.prepareStatement(notaB);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaParcialB=stmt.executeQuery();
			if(notaParcialB.next()){
				listaNotas.add(notaParcialB.getInt("notaParcialB"));
			}

			String notaMB="Select Count (distinct IdentificacaoAluno) as 'notaParcialMB' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoParcial = 'MB' ";
			stmt=con.prepareStatement(notaMB);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaParcialMB=stmt.executeQuery();
			if(notaParcialMB.next()){
				listaNotas.add(notaParcialMB.getInt("notaParcialMB"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaNotas;
	}



	public List<Integer> estatisticaDisciplinaFinal(int selecaoTurma, int selecaoDisciplina){
		List<Integer> listaNotas=new ArrayList<Integer>();
		try{
			Connection con = BD.getConnection();
			String notaI="Select Count (distinct IdentificacaoAluno) as 'notaFinalI' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ?  and conceitoFinal = 'I' ";
			PreparedStatement stmt=con.prepareStatement(notaI);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaFinalI=stmt.executeQuery();
			if(notaFinalI.next()){
				listaNotas.add(notaFinalI.getInt("notaFinalI"));
			}

			String notaR="Select Count (distinct IdentificacaoAluno) as 'notaFinalR' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoFinal = 'R' ";
			stmt=con.prepareStatement(notaR);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaFinalR=stmt.executeQuery();
			if(notaFinalR.next()){
				listaNotas.add(notaFinalR.getInt("notaFinalR"));
			}

			String notaB="Select Count (distinct IdentificacaoAluno) as 'notaFinalB' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoFinal = 'B' ";
			stmt=con.prepareStatement(notaB);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaFinalB=stmt.executeQuery();
			if(notaFinalB.next()){
				listaNotas.add(notaFinalB.getInt("notaFinalB"));
			}

			String notaMB="Select Count (distinct IdentificacaoAluno) as 'notaFinalMB' from Boletim Where IDDaTurma = ? and IDDaDisciplina = ? and conceitoFinal = 'MB' ";
			stmt=con.prepareStatement(notaMB);
			stmt.setInt(1, selecaoTurma);
			stmt.setInt(2, selecaoDisciplina);

			ResultSet notaFinalMB=stmt.executeQuery();
			if(notaFinalMB.next()){
				listaNotas.add(notaFinalMB.getInt("notaFinalMB"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaNotas;
	}


	public List<Integer> localizaDadosComparativos(int selecaoCurso, int selecaoModulo, int selecaoAno, int selecaoSemestre){
		List<Integer> dadosLocalizados=new ArrayList<Integer>();
		int contador=0;
		int retencaoNota=0;
		int aprovacao=0;

		try{
			Connection con = BD.getConnection();
			String reprovadoFaltas = "Select  Count (distinct IdentificacaoAluno) as 'retidoFaltas' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and statusFinal = 'Retido por Faltas' ";

			PreparedStatement stmt=con.prepareStatement(reprovadoFaltas);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);

			ResultSet retidoFaltas=stmt.executeQuery();
			if(retidoFaltas.next()){
				int estatistica;
				estatistica = retidoFaltas.getInt("retidoFaltas");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);    
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;
			String reprovadoNotas = "Select identificacaoAluno, Count (distinct statusFinal) as 'retidoNotas' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and statusFinal = 'Retido por Nota' " +
					"Group by identificacaoAluno " +
					"having Count (*) > = 3 ";

			stmt=con.prepareStatement(reprovadoNotas);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);

			ResultSet retidoNotas=stmt.executeQuery();
			while(retidoNotas.next()){
				int estatistica;
				estatistica = retidoNotas.getInt("retidoNotas");
				contador=contador+1;
				retencaoNota=retencaoNota+1;
			}

			if(contador>0){
				dadosLocalizados.add(contador);     
			}
			else{
				dadosLocalizados.add(0);
			}


			contador=0;
			String promovido = "Select Count (distinct IdentificacaoAluno) as 'promovido' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ?  and turma.ano = ? and turma.semestre = ? and statusFinal = 'Promovido' ";

			stmt=con.prepareStatement(promovido);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			ResultSet aprovado=stmt.executeQuery();
			if(aprovado.next()){

				int estatistica;
				estatistica = aprovado.getInt("promovido");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);
				aprovacao = aprovacao+contador;
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;
			String promovidoComProgressaoParcial = "Select identificacaoAluno, Count (distinct statusFinal) as 'retidoNotas' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and statusFinal = 'Retido por Nota' " +
					"Group by identificacaoAluno " +
					"having Count (*) <=2 ";
			stmt=con.prepareStatement(promovidoComProgressaoParcial);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);

			ResultSet promovidoComProgressoesParciais=stmt.executeQuery();
			contador=0;
			while(promovidoComProgressoesParciais.next()){
				int estatistica=0;
				estatistica = promovidoComProgressoesParciais.getInt("retidoNotas");
				contador=contador+1;
				retencaoNota=retencaoNota+1;
			}

			if(contador>0){
				dadosLocalizados.add(contador);     
			}
			else{
				dadosLocalizados.add(0);
			}

			aprovacao=aprovacao-retencaoNota;
			if(aprovacao>0){
				dadosLocalizados.set(2, aprovacao);
			}
			else{
				dadosLocalizados.set(2, 0);
			}


		}

		catch(SQLException e){
			e.printStackTrace();
		}

		return dadosLocalizados;
	}


	public int eliminaRedundanciaTurma(int selecaoTurma) {
		int redundancia = 0;
		List<String> promovido=new ArrayList<String>();
		List<String> retidoNota=new ArrayList<String>();
		try{
			Connection con = BD.getConnection();
			String aprovado = "Select distinct IdentificacaoAluno as 'identificacaoAluno' from Boletim Where IDDaTurma = ? and statusFinal = 'Promovido' ";
			PreparedStatement stmt=con.prepareStatement(aprovado);
			stmt.setInt(1, selecaoTurma);
			ResultSet promovidoLocalizado=stmt.executeQuery();
			while(promovidoLocalizado.next()){
				promovido.add(promovidoLocalizado.getString("identificacaoAluno"));
			}

			String reprovadoNota = "Select distinct IdentificacaoAluno as 'identificacaoAluno' from Boletim Where IDDaTurma = ? and statusFinal = 'Retido por nota' ";

			stmt=con.prepareStatement(reprovadoNota);
			stmt.setInt(1, selecaoTurma);
			ResultSet reprovadoNotas=stmt.executeQuery();
			while(reprovadoNotas.next()){
				retidoNota.add(reprovadoNotas.getString("identificacaoAluno"));
			}


			for (int i=0;i<retidoNota.size();i++){
				String auxReprovado=retidoNota.get(i);

				for(int j=0; j<promovido.size();j++){
					String auxAprovado=promovido.get(j);


					if(auxReprovado.equalsIgnoreCase(auxAprovado)){
						redundancia=redundancia+1;
					}

				}
			}

		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return redundancia;
	}

	public int eliminaRedundanciaComparacao(int selecaoCurso, int selecaoModulo, int selecaoAno, int selecaoSemestre){
		int redundancia = 0;
		List<String> promovido=new ArrayList<String>();
		List<String> retidoNota=new ArrayList<String>();
		try{
			Connection con = BD.getConnection();
			String aprovado = "Select distinct identificacaoAluno "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ?  and turma.ano = ? and turma.semestre = ? and statusFinal = 'Promovido' ";
			PreparedStatement stmt=con.prepareStatement(aprovado);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			ResultSet promovidoLocalizado=stmt.executeQuery();
			while(promovidoLocalizado.next()){
				promovido.add(promovidoLocalizado.getString("identificacaoAluno"));
			}

			String reprovadoNota = "Select distinct identificacaoAluno "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"Where turma.curso = ? and turma.modulo = ?  and turma.ano = ? and turma.semestre = ? and statusFinal = 'Retido por nota' ";
			stmt=con.prepareStatement(reprovadoNota);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			ResultSet reprovadoNotas=stmt.executeQuery();
			while(reprovadoNotas.next()){
				retidoNota.add(reprovadoNotas.getString("identificacaoAluno"));
			}


			for (int i=0;i<retidoNota.size();i++){
				String auxReprovado=retidoNota.get(i);

				for(int j=0; j<promovido.size();j++){
					String auxAprovado=promovido.get(j);


					if(auxReprovado.equalsIgnoreCase(auxAprovado)){
						redundancia=redundancia+1;
					}

				}
			}


		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return redundancia;
	}


	public List<Integer> localizaDadosAluno(int IDDaTurma, String identificacao, int selecaoModulo){
		boolean efetuado=false;
		List<Integer> mencoes=new ArrayList<Integer>();
		try{
			Connection con = BD.getConnection();
			String notaFinalI= "Select Count (identificacaoAluno) as 'conceitoFinalI' from Boletim Where IDDaTurma = ? and identificacaoAluno = ? and conceitoFinal = 'I' ";
			PreparedStatement stmt=con.prepareStatement(notaFinalI);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			ResultSet mencaoI=stmt.executeQuery();
			if(mencaoI.next()){
				mencoes.add(mencaoI.getInt("conceitoFinalI"));
			}

			String notaFinalR = "Select Count (identificacaoAluno) as 'conceitoFinalR' from Boletim Where IDDaTurma = ? and identificacaoAluno = ? and conceitoFinal = 'R' ";
			stmt=con.prepareStatement(notaFinalR);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			ResultSet mencaoR=stmt.executeQuery();
			if(mencaoR.next()){
				mencoes.add(mencaoR.getInt("conceitoFinalR"));
			}


			String notaFinalB = "Select Count (identificacaoAluno) as 'conceitoFinalB' from Boletim Where IDDaTurma = ? and identificacaoAluno = ? and conceitoFinal = 'B' ";
			stmt=con.prepareStatement(notaFinalB);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			ResultSet mencaoB=stmt.executeQuery();
			if(mencaoB.next()){
				mencoes.add(mencaoB.getInt("conceitoFinalB"));
			}

			String notaFinalMB = "Select Count (identificacaoAluno) as 'conceitoFinalMB' from Boletim Where IDDaTurma = ? and identificacaoAluno = ? and conceitoFinal = 'MB' ";
			stmt=con.prepareStatement(notaFinalMB);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			ResultSet mencaoMB=stmt.executeQuery();
			if(mencaoMB.next()){
				mencoes.add(mencaoMB.getInt("conceitoFinalMB"));
			}


		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return mencoes;
	}


	public List<Integer> localizaDadosAreaAluno(int IDDaTurma, String identificacao, int selecaoModulo, int selecaoArea){
		boolean efetuado=false;
		List<Integer> mencoes=new ArrayList<Integer>();
		try{
			Connection con = BD.getConnection();
			String notaFinalI= "Select Count (identificacaoAluno) as 'conceitoFinalI' from Boletim  "+
					"inner join Disciplina "+
					"on Boletim.IDDaDisciplina = disciplina.IDDisciplina "+
					"Where IDDaTurma = ? and identificacaoAluno = ? and Disciplina.AreaConcentracao = ? and conceitoFinal = 'I' ";
			PreparedStatement stmt=con.prepareStatement(notaFinalI);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			stmt.setInt(3, selecaoArea);
			ResultSet mencaoI=stmt.executeQuery();
			if(mencaoI.next()){
				mencoes.add(mencaoI.getInt("conceitoFinalI"));
			}

			String notaFinalR = "Select Count (identificacaoAluno) as 'conceitoFinalR' from Boletim "+
					"inner join disciplina "+
					"on Boletim.IDDaDisciplina = disciplina.IDDisciplina "+
					"Where IDDaTurma = ? and identificacaoAluno = ? and Disciplina.areaConcentracao = ? and conceitoFinal = 'R' ";
			stmt=con.prepareStatement(notaFinalR);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			stmt.setInt(3, selecaoArea);
			ResultSet mencaoR=stmt.executeQuery();
			if(mencaoR.next()){
				mencoes.add(mencaoR.getInt("conceitoFinalR"));
			}


			String notaFinalB = "Select Count (identificacaoAluno) as 'conceitoFinalB' from  Boletim  "+
					"inner join Disciplina "+
					"on Boletim.IDDaDisciplina = Disciplina.IDDisciplina "+
					"Where IDDaTurma = ? and identificacaoAluno = ? and Disciplina.AreaConcentracao = ? and conceitoFinal = 'B' ";
			stmt=con.prepareStatement(notaFinalB);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			stmt.setInt(3, selecaoArea);
			ResultSet mencaoB=stmt.executeQuery();
			if(mencaoB.next()){
				mencoes.add(mencaoB.getInt("conceitoFinalB"));
			}

			String notaFinalMB = "Select Count (identificacaoAluno) as 'conceitoFinalMB' "+
					"from Boletim "+
					"inner join disciplina "+
					"on Boletim.IDDaDisciplina = disciplina.IDDisciplina "+
					"Where IDDaTurma = ? and identificacaoAluno = ? and Disciplina.AreaConcentracao  = ? and conceitoFinal = 'MB'";
			stmt=con.prepareStatement(notaFinalMB);
			stmt.setInt(1, IDDaTurma);
			stmt.setString(2, identificacao);
			stmt.setInt(3, selecaoArea);
			ResultSet mencaoMB=stmt.executeQuery();
			if(mencaoMB.next()){
				mencoes.add(mencaoMB.getInt("conceitoFinalMB"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return mencoes;
	}

	public List<Turma> localizaTurmaAluno(String identificacaoAluno, int selecaoCurso){
		List<Turma> listaTurmas=new ArrayList<Turma>();
		int cursoLocalizado=0;
		int turmaLocalizada=0;
		try{
			Connection con = BD.getConnection();
			String sql = "Select Turma.IDTurma, Turma.modulo, Turma.semestre, Turma.ano " +
					"from Turma " +
					"inner join AlunoTurma "+
					"on Turma.IDTurma=AlunoTurma.IDDaTurma "+
					"Where AlunoTurma.IdentificacaoAluno = ? and turma.curso= ? " +
					"order by Turma.modulo ";

			PreparedStatement stmt=con.prepareStatement(sql);
			stmt.setString(1, identificacaoAluno);
			stmt.setInt(2, selecaoCurso);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				Turma t=new Turma();
				t.setIDTurma(rs.getInt("IDTurma"));
				t.setModulo(rs.getInt("modulo"));
				t.setSemestre(rs.getInt("semestre"));
				t.setAno(rs.getInt("ano"));
				listaTurmas.add(t);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return listaTurmas;
	}


	public List<Integer> localizaDadosAreaTurma(int selecaoCurso, int selecaoModulo, int selecaoAno, int selecaoSemestre, int selecaoArea){
		List<Integer> dadosLocalizados=new ArrayList<Integer>();
		int contador=0;
		int retencaoNota=0;
		int aprovacao=0;

		try{
			Connection con = BD.getConnection();
			String reprovadoFaltas = "Select  Count (distinct IdentificacaoAluno) as 'retidoFaltas' "+  
					"from Boletim "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+ 
					"inner join Disciplina "+
					"on Boletim.IDDaDisciplina  = Disciplina.IDDisciplina "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and disciplina.areaConcentracao = ? "+ 
					"and statusFinal = 'Retido por Faltas' ";

			PreparedStatement stmt=con.prepareStatement(reprovadoFaltas);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			stmt.setInt(5, selecaoArea);

			ResultSet retidoFaltas=stmt.executeQuery();
			if(retidoFaltas.next()){
				int estatistica;
				estatistica = retidoFaltas.getInt("retidoFaltas");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);    
			}
			else{
				dadosLocalizados.add(0);
			}

			contador=0;
			String reprovadoNotas = "Select identificacaoAluno, Count (distinct statusFinal) as 'retidoNotas' "+ 
					"from Boletim "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+ 
					"inner join Disciplina "+
					"on Boletim.IDDaDisciplina = Disciplina.IDDisciplina "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and Disciplina.areaConcentracao = ? and statusFinal= 'Retido por Nota' "+ 
					"Group by identificacaoAluno "+ 
					"having Count (*) > = 3 ";


			stmt=con.prepareStatement(reprovadoNotas);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			stmt.setInt(5, selecaoArea);

			ResultSet retidoNotas=stmt.executeQuery();
			while(retidoNotas.next()){
				int estatistica;
				estatistica = retidoNotas.getInt("retidoNotas");
				contador=contador+1;
				retencaoNota=retencaoNota+1;
			}

			if(contador>0){
				dadosLocalizados.add(contador);     
			}
			else{
				dadosLocalizados.add(0);
			}


			contador=0;
			String promovido = "Select Count (distinct IdentificacaoAluno) as 'promovido' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"inner join disciplina "+
					"on boletim.IDDaDisciplina = disciplina.IDDisciplina "+
					"Where turma.curso = ? and turma.modulo = ?  and turma.ano = ? and turma.semestre = ? and disciplina.areaConcentracao = 1 and statusFinal = 'Promovido'";

			stmt=con.prepareStatement(promovido);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			ResultSet aprovado=stmt.executeQuery();
			if(aprovado.next()){

				int estatistica;
				estatistica = aprovado.getInt("promovido");
				contador=estatistica;
			}

			if(contador>0){
				dadosLocalizados.add(contador);
				aprovacao = aprovacao+contador;
			}
			else{
				dadosLocalizados.add(0);
			}



			contador=0;
			String promovidoComProgressaoParcial = "Select identificacaoAluno, Count (distinct statusFinal) as 'retidoNotas' "+ 
					"from Boletim  "+
					"inner join turma "+
					"on Boletim.IDDaTurma=Turma.IDTurma "+
					"inner join Disciplina "+
					"on Boletim.IDDaDisciplina = Disciplina.IDDisciplina "+
					"Where turma.curso = ? and turma.modulo = ? and turma.ano = ? and turma.semestre = ? and disciplina.areaConcentracao = ? and statusFinal = 'Retido por Nota' " +
					"Group by identificacaoAluno " +
					"having Count (*) <=2 ";
			stmt=con.prepareStatement(promovidoComProgressaoParcial);
			stmt.setInt(1, selecaoCurso);
			stmt.setInt(2, selecaoModulo);
			stmt.setInt(3, selecaoAno);
			stmt.setInt(4, selecaoSemestre);
			stmt.setInt(5, selecaoArea);
			ResultSet promovidoComProgressoesParciais=stmt.executeQuery();
			contador=0;
			while(promovidoComProgressoesParciais.next()){
				int estatistica=0;
				estatistica = promovidoComProgressoesParciais.getInt("retidoNotas");
				contador=contador+1;
				retencaoNota=retencaoNota+1;
			}

			if(contador>0){
				dadosLocalizados.add(contador);     
			}
			else{
				dadosLocalizados.add(0);
			}

			aprovacao=aprovacao-retencaoNota;
			if(aprovacao>0){
				dadosLocalizados.set(2, aprovacao);
			}
			else{
				dadosLocalizados.set(2, 0);
			}


		}

		catch(SQLException e){
			e.printStackTrace();
		}

		return dadosLocalizados;
	}
	
	
    public List<Integer> estatisticaConcluintes (List<String>listaIdentificacoes, int curso, int disciplinasDoCurso, int matriculados){
    int concluintes=0;
    int naoConcluintes=0;
    int disciplinasAprovadas=0;
    int estudantesTrancados=0;
    int trancados=0;
    List<Integer> formandos=new ArrayList<Integer>();
    try{
    Connection con= BD.getConnection();
    for (String identificacaoAluno: listaIdentificacoes){
    String sql = "select Count (distinct IDDaDisciplina) as 'IDDaDisciplina' "+
"from Boletim where identificacaoAluno = ? and statusFinal = 'Promovido' ";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    disciplinasAprovadas = rs.getInt("IDDaDisciplina");
     }
        
    if(disciplinasAprovadas == disciplinasDoCurso){
    concluintes=concluintes+1;
    }
    
    sql = "select identificacaoAluno from Trancamento where identificacaoAluno = ?";
    stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacaoAluno);
    rs=stmt.executeQuery();
    if(rs.next()){
    estudantesTrancados=estudantesTrancados+1;
    }
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    
    naoConcluintes = matriculados-concluintes;
    formandos.add(concluintes);
    formandos.add(naoConcluintes);
    formandos.add(estudantesTrancados);
    
    return formandos;
    }
    
    
    public int localizaAluno(String identificacao){
    int localizado=0;
    try{
    Connection con = BD.getConnection();
    String sql = "Select identificacaoAluno from Trancamento where identificacaoAluno = ?";
    PreparedStatement stmt=con.prepareStatement(sql);
    stmt.setString(1, identificacao);
    ResultSet rs=stmt.executeQuery();
    if(rs.next()){
    localizado=1;
    }
    }
    catch(SQLException e){
    e.printStackTrace();
    }
    return localizado;
    }
    

}
