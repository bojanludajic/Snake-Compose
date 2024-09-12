package data.repository

import data.model.GameData
import data.model.UserData
import java.sql.DriverManager
import java.sql.ResultSet

class GameRepository {
    val url = "jdbc:mysql://localhost:3306/sys"
    val user = "root"
    val password = "bnbn1616"

    fun getGames(): List<GameData> {
        val gameDataList = mutableListOf<GameData>()
        DriverManager.getConnection(url, user, password).use { connection ->
            connection.createStatement().use {statement ->
                val query = "SELECT u.iduser, u.name, g.idgame, g.score " +
                        "FROM `user-score`.user u JOIN `user-score`.game g ON u.iduser = g.iduser"
                val resultSet: ResultSet = statement.executeQuery(query)

                while(resultSet.next()) {
                    val userId = resultSet.getInt("iduser")
                    val name = resultSet.getString("name")
                    val gameId = resultSet.getInt("idgame")
                    val score = resultSet.getInt("score")
                    gameDataList.add(GameData(gameId, score, userId, name))
                }
            }
            return gameDataList
        }
    }

    fun getUsers(): List<UserData> {
        val userDataList = mutableListOf<UserData>()
        DriverManager.getConnection(url, user, password).use { connection ->
            connection.createStatement().use { statement ->
                val query = "SELECT * FROM `user-score`.user"
                val resultSet: ResultSet = statement.executeQuery(query)

                while(resultSet.next()) {
                    val id = resultSet.getInt("iduser")
                    val name = resultSet.getString("name")
                    userDataList.add(UserData(id,name))
                }
                return userDataList
            }
        }
    }

    fun insertNewScore(userId: Int, score: Int) {
        DriverManager.getConnection(url, user, password).use {connection ->
            val query = "INSERT INTO `user-score`.game(iduser,score) " +
                    "VALUES(?,?)"
            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setInt(1,userId)
                preparedStatement.setInt(2,score)

                preparedStatement.executeUpdate()
            }

        }
    }

    fun insertUser(name: String) {
        val url = "jdbc:mysql://localhost:3306/sys"
        val user = "root"
        val password = "bnbn1616"

        DriverManager.getConnection(url, user, password).use {connection ->
            val query = "INSERT INTO `user-score`.user(name) VALUES(?)"

            connection.prepareStatement(query).use { preparedStatement ->
                preparedStatement.setString(1, name)

                preparedStatement.executeUpdate()
            }

        }
    }
}