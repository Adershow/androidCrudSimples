package neves.com.br.crudtrescamadassimples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diego on 13/10/2017.
 */

public class CachorroBD extends SQLiteOpenHelper{
    private static String TAG = "bancox";
    private static final String NOME_BD = "cachorros.sqlite";
    private static final int VERSAO = 10;
    private static CachorroBD cachorroBD = null; //Singleton

    public CachorroBD(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BD, null, VERSAO);
    }

    public static CachorroBD getInstance(Context context){
        if(cachorroBD == null){
            cachorroBD = new CachorroBD(context);
            return cachorroBD;
        }else{
            return cachorroBD;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists cachorro" +
                "( _id integer primary key autoincrement, " +
                " nome text, " +
                " raca text, " +
                " sexo text," +
                " tamanho text,"+
                " imagem blob);";
        Log.d(TAG, "Criando a tabela cachorro. Aguarde ...");
        db.execSQL(sql);
        Log.d(TAG, "Tabela cachorro criada");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        // exemplo:
        Log.d("teste", "Upgrade da versão " + oldVersion + " para "
                + newVersion + ", destruindo tudo.");
        db.execSQL("DROP TABLE IF EXISTS cachorro");
        onCreate(db); // chama onCreate e recria o banco de dados
        Log.i("teste", "Executou o script de upgrade da tabela cachorro.");

    }

    public long save(Cachorro cachorro){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("nome", cachorro.nome);
            values.put("sexo", cachorro.sexo);
            values.put("raca", cachorro.raca);
            values.put("tamanho", cachorro.tamanho);
            values.put("imagem", cachorro.imagem);
            if(cachorro._id == null){
                //insere no banco de dados
                return db.insert("cachorro", null, values);
            }else{//altera no banco de dados
                values.put("_id", cachorro._id);
                return db.update("cachorro", values, "_id=" + cachorro._id, null);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            db.close(); //não esquecer de liberar o recurso
        }
        return 0; //caso não realize as operações
    }

    public long delete(Cachorro cachorro){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
        try{
            return db.delete("cachorro", "_id=?", new String[]{String.valueOf(cachorro._id)});
        }
        finally {
            db.close(); //não esquecer de liberar o recurso
        }
    }

    //retorna a lista de cachorros
    public List<Cachorro> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        try {
            //retorna uma List para os registros contidos no banco de dados
            // select * from cachorro
            return toList(db.rawQuery("SELECT  * FROM cachorro", null));
        } finally {
            db.close();
        }
    }

    public List<Cachorro> getByname(String nome){
        SQLiteDatabase db = getReadableDatabase();
        try {
            //retorna uma List para os registros contidos no banco de dados
            // select * from cachorro
            return toList(db.rawQuery("SELECT  * FROM cachorro where nome LIKE'" + nome + "%'", null));
        } finally {
            db.close();
        }
    }

    //converte de Cursor para List
    private List<Cachorro> toList(Cursor c) {
        List<Cachorro> cachorros = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Cachorro cachorro = new Cachorro();

                // recupera os atributos do cursor para o cachorro
                cachorro._id = c.getLong(c.getColumnIndex("_id"));
                cachorro.nome = c.getString(c.getColumnIndex("nome"));
                cachorro.raca = c.getString(c.getColumnIndex("raca"));
                cachorro.sexo = c.getString(c.getColumnIndex("sexo"));
                cachorro.tamanho = c.getString(c.getColumnIndex("tamanho"));
                cachorro.imagem = c.getBlob(c.getColumnIndex("imagem"));
                cachorros.add(cachorro);

            } while (c.moveToNext());
        }
        return cachorros;
    }
}
