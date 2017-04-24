package zadatak5.zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import zadatak5.model.Artikal;
import zadatak5.model.Racun;
import zadatak5.model.Stavka;

import java.io.IOException;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak1KreiranjeTabela {
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");

            TableUtils.dropTable(connectionSource, Stavka.class, true);
            TableUtils.dropTable(connectionSource, Artikal.class, true);
            TableUtils.dropTable(connectionSource, Racun.class, true);

            TableUtils.createTable(connectionSource, Artikal.class);
            TableUtils.createTable(connectionSource, Racun.class);
            TableUtils.createTable(connectionSource, Stavka.class);


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
