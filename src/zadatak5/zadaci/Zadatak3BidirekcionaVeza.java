package zadatak5.zadaci;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import zadatak5.model.Artikal;
import zadatak5.model.Racun;
import zadatak5.model.Stavka;

import java.io.IOException;
import java.util.List;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak3BidirekcionaVeza {

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

            System.out.println("-----------------");
            List<Racun>racuni = racunDao.queryForAll();
            for(Racun r : racuni)
                System.out.println(r);

            System.out.println("-----------------");
            System.out.println("-----------------");
            racuni = racunDao.queryForEq(Racun.POLJE_OZNAKA, "Racun2");
            for (Racun r : racuni)
                System.out.println(r);
            System.out.println("-----------------");

            ForeignCollection<Stavka> stavkeRacuna = racuni.get(0).getStavke();

            CloseableIterator<Stavka> iterator = stavkeRacuna.closeableIterator();

            System.out.println("-----------------");

            try{
                while(iterator.hasNext()){
                    Stavka s = iterator.next();
                    System.out.println("s = " + s);
                }
            }catch(Exception e){
                System.out.println("Greska prilikom iteracije");
            }
            finally {
                iterator.close();
            }
            System.out.println("-----------------");









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
