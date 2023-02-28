package hawk.service

import hawk.model.Search
import hawk.model.User
import org.hibernate.Session
import java.sql.Connection
import java.util.logging.Level
import java.util.logging.Logger
import javax.persistence.EntityManager

class UserSearchService(

    private val entityManager: EntityManager
) {
    private val logger = Logger.getLogger(UserSearchService::class.java.name)

    fun search(search: Search): List<User?>? {
        val session = entityManager.unwrap(Session::class.java)
        return session.doReturningWork<List<User?>> { connection: Connection ->
            val usersList: MutableList<User> = ArrayList<User>()
            // The wrong way
            val query = "select id, name, description from public.users where name like '%" +
                search.searchText + "%'"
            logger.log(Level.INFO, "SQL Query {0}", query)
            val rs = connection
                .createStatement()
                .executeQuery(query)

            /* The righter way, should probably use built in Data Model for this, but this is safe
                String query = "select id, name, description from ITEM where description like ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, "%" + search.getSearchText() + "%");
                LOGGER.log(Level.INFO, "SQL Query {0}",  statement);
                ResultSet rs = statement.executeQuery();
            */

            while (rs.next()) {
                usersList.add(
                    User(
                        id = rs.getLong("id"),
                        name = rs.getString("name"),
                        description = rs.getString("description")
                    )
                )
            }
            rs.close()
            usersList
        }
    }
}
