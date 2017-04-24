package zadatak5.zadaci;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;

/**
 * Created by Nikola on 4/22/2017.
 */
public class Zadatak5BrisanjeRacunaStavkiTransakcija {
    public static void main(String[] args) {
        ConnectionSource connectionSource = null;

        try{
            connectionSource = new JdbcConnectionSource("jdbc:sqlite:knjigaOblast.db");
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
