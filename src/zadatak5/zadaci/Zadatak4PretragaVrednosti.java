package zadatak5.zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import zadatak5.model.Artikal;
import zadatak5.model.Racun;
import zadatak5.model.Stavka;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak4PretragaVrednosti {
    static Dao<Artikal, Integer> artikalDao;
    static Dao<Racun, Integer> racunDao;
    static Dao<Stavka, Integer> stavkaDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            artikalDao = DaoManager.createDao(connectionSource, Artikal.class);
            racunDao = DaoManager.createDao(connectionSource, Racun.class);
            stavkaDao = DaoManager.createDao(connectionSource, Stavka.class);

            // SELECT IZRAZ ZA TRAZENJE I ISPIS
            QueryBuilder<Artikal, Integer> artikalCenaNazivQuery = artikalDao.queryBuilder();
            Where<Artikal, Integer> where = artikalCenaNazivQuery.where();
            where.like(Artikal.POLJE_NAZIV, "%hleb%");
            PreparedQuery<Artikal>pripremljenArtikal = artikalCenaNazivQuery.prepare();
            List<Artikal> artikal = artikalDao.query(pripremljenArtikal);
            for(Artikal a : artikal)
                System.out.println("a = " + a);


            //  SELECARG
            SelectArg selektZaOpis = new SelectArg();
            PreparedQuery<Artikal> artikalQueryPriprenljen = artikalDao.queryBuilder().where().like(Artikal.POLJE_OPIS,selektZaOpis).prepare();
            selektZaOpis.setValue("%1L%");
            artikal = artikalDao.query(artikalQueryPriprenljen);
            for(Artikal a : artikal)
                System.out.println("a = " + a);

            selektZaOpis.setValue("%sa%");
            artikal=artikalDao.query(artikalQueryPriprenljen);
            for(Artikal a : artikal)
                System.out.println("a = " + a);

            // SELECT SA JOIN
            QueryBuilder<Racun, Integer> racunQuery = racunDao.queryBuilder();
            QueryBuilder<Stavka, Integer> stavkaQuery = stavkaDao.queryBuilder();
            stavkaQuery.where().eq(Stavka.POLJE_KOLICINA,2);
            List<Racun>racun = racunQuery.join(stavkaQuery).query();
            for(Racun r : racun)
                System.out.println("r = " + r);


        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connectionSource != null) {
                try {
                    connectionSource.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
