import java.sql.*;

public class Database {

    public int getTitles(String artistName) {
        String USERNAME = Credentials.USERNAME;
        String PASSWORD = Credentials.PASSWORD;
        String URL = Credentials.URL;
        int titleNum = 0;
        String sql = "select count(*) from " +
                "(select album.artistid, album.title, artist.name from " +
                "artist inner join album on album.artistid = artist.artistid " +
                "where name = ?) as count";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, artistName);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
                titleNum =  resultSet.getInt("count");
            stmt.close();
            resultSet.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return titleNum;
    }

    public boolean establishDBConnection() {
        String USERNAME = Credentials.USERNAME;
        String PASSWORD = Credentials.PASSWORD;
        String URL = Credentials.URL;
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return con.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}