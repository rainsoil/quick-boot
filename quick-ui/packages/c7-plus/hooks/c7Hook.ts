// hooks/useFetchOptions.ts
import { ref } from 'vue'
import { jsonGet } from '../utils/utils'

export function useFetchOptions({
                                    fetchData,
                                    fetchParams = {},
                                    resultKey = 'data',
                                    dataFormatter = null,
                                    dataList = []
                                }) {
    const options = ref([])
    const loading = ref(false)

    const fetchAndUpdate = async (query = '') => {
        if (!fetchData) {
            options.value = dataList
            return
        }

        loading.value = true
        try {
            const params = { ...fetchParams, query }
            const result = await fetchData(params)
            let list = jsonGet(result, resultKey, [])
            if (typeof dataFormatter === 'function') {
                list = dataFormatter(list)
            }
            options.value = Array.isArray(list) ? list : []
        } catch (err) {
            console.error('useFetchOptions error:', err)
            options.value = []
        } finally {
            loading.value = false
        }
    }

    return {
        options,
        loading,
        fetchAndUpdate,
    }
}
