package zadatak5.zadaci;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import zadatak5.model.Artikal;
import zadatak5.model.Racun;
import zadatak5.model.Stavka;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak2DodavanjeBrisanjeIzmenaVrednosti {

    static Dao <Artikal, Integer> artikalDao;
    static Dao <Racun, Integer> racunDao;
    static Dao <Stavka, Integer> stavkaDao;

    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            artikalDao = DaoManager.createDao(connectionSource, Artikal.class);
            racunDao = DaoManager.createDao(connectionSource, Racun.class);
            stavkaDao = DaoManager.createDao(connectionSource, Stavka.class);

            TableUtils.clearTable(connectionSource, Stavka.class);
            TableUtils.clearTable(connectionSource, Artikal.class);
            TableUtils.clearTable(connectionSource,Racun.class);

            //-------------------------------------------------

            Artikal a1 = new Artikal("Mleko", "Mleko u flasi 1L", 40.99);
            artikalDao.create(a1);
            Artikal a2 = new Artikal("Beli hleb", "400g", 50);
            artikalDao.create(a2);
            Artikal a3 = new Artikal("Crni hleb", "Crni razeni hleb", 60);
            artikalDao.create(a3);
            Artikal a4 = new Artikal("Jogurt", "Jogurt u tetrapaku 1L", 90.99);
            artikalDao.create(a4);

            //-------------------------------------------------

            Racun r1 = new Racun("Racun1", new Date());
            racunDao.create(r1);
            Racun r2 = new Racun("Racun2", new Date());
            racunDao.create(r2);

            //-------------------------------------------------

            Stavka s1 = new Stavka(1, r1, a1);
            stavkaDao.create(s1);
            Stavka s2 = new Stavka(2, r1, a2);
            stavkaDao.create(s2);
            Stavka s3 = new Stavka(3, r2, a3);
            stavkaDao.create(s3);

            //-------------------------------------------------


            //-------------------------------------------------
            Racun racunZaIzmenu = racunDao.queryForId(r1.getId());
            racunZaIzmenu.setOznaka("Prvi racun");
            racunDao.update(racunZaIzmenu);

            List<Racun> racuni = racunDao.queryForAll();
            for (Racun j : racuni)
                System.out.println(j);
            //-------------------------------------------------


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
