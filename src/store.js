import { reactive } from 'vue';

export const store = reactive({
    searchQuery: '',
    setSearchQuery(query) {
        this.searchQuery = query;
    }
});
