package Dao;

public interface BackupRestoreDao {
public boolean efetuarBackupSimples(String database, String diretorio);
public boolean efetuarRestoreSimples(String database, String diretorio);
public boolean verificaExistencia (String database, String diretorio, String sobrepor);
}
