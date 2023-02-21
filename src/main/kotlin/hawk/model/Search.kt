package hawk.model

class Search {

    private var searchText: String? = null

    fun Search() {}

    fun Search(searchText: String?) {
        this.searchText = searchText
    }

    fun getSearchText(): String? {
        return searchText
    }

    fun setSearchText(searchText: String?) {
        this.searchText = searchText
    }
}
