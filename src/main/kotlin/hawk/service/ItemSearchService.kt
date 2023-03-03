package hawk.service

import hawk.model.Item
import hawk.model.Search
import org.hibernate.Session
import java.util.logging.Level
import java.util.logging.Logger
import javax.persistence.EntityManager

class ItemSearchService(private val entityManager: EntityManager) {

    private val logger = Logger.getLogger(ItemSearchService::class.java.name)

    fun search(search: Search): List<Item?>? {
        val session = entityManager.unwrap(Session::class.java) as Session
        return session.doReturningWork<List<Item?>> { connection ->
            logger.info("Search text : $search")
            val items: MutableList<Item> = ArrayList()
            // The wrong way
            val query = "select id, name, description from ITEM where description like '%" +
                search.searchText + "%'"
            logger.log(Level.INFO, "SQL Query: {0}", query)
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
                items.add(
                    Item(
                        id = rs.getLong("id"),
                        name = rs.getString("name"),
                        description = rs.getString("description")
                    )
                )
            }
            rs.close()
            items
        }
    }
}
