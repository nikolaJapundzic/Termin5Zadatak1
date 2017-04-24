package zadatak5.zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import zadatak5.model.Artikal;
import zadatak5.model.Racun;
import zadatak5.model.Stavka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak5BrisanjeRacunaStavkiTransakcija {
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

            List<Racun> racuni = racunDao.queryForEq(Racun.POLJE_OZNAKA, "Racun2");
            for (Racun r : racuni)
                System.out.println(r);
            System.out.println("-----------------");

            PreparedQuery<Racun> racunPriprenljen = racunDao.queryBuilder().where().eq(Racun.POLJE_OZNAKA,"Racun2").prepare();
            final Racun r2 = racunDao.queryForFirst(racunPriprenljen);
            if(r2 != null){
                final ArrayList<Stavka>zaBrisanje = new ArrayList<Stavka>();

                ForeignCollection<Stavka> stavkeRacuna = r2.getStavke();
                CloseableIterator<Stavka> iterator = stavkeRacuna.closeableIterator();

                try {
                    while(iterator.hasNext()){
                        Stavka s = iterator.next();
                        zaBrisanje.add(s);
                        System.out.println("Stavka za brisanje: " + s);
                    }
                }catch (Exception e)
                {
                    System.out.println("Greska prilikom iteracije");
                }
                finally {
                    iterator.close();
                }

                TransactionManager.callInTransaction(connectionSource,
                        new Callable<Void>() {
                            public Void call() throws Exception{
                                stavkaDao.delete(zaBrisanje);
                                racunDao.delete(r2);
                                return null;
                            }

                         });
            }

            racuni = racunDao.queryForAll();
            for (Racun r : racuni)
                System.out.println(r);


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
