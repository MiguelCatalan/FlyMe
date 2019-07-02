package info.miguelcatalan.flyme.data.repository

import org.junit.Before
import org.junit.Test

class RxInMemoryCacheDataSourceTests {

    lateinit var rxInMemoryCacheDataSourceMother: RxInMemoryCacheDataSourceMother

    @Before
    fun setUp() {
        rxInMemoryCacheDataSourceMother = RxInMemoryCacheDataSourceMother()
    }

    @Test
    fun `should complete get by key without value for non existent key`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get by key without value when there are no items `() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value when key is null`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all with emptylist when key is empty`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf())
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { values ->
            values.isEmpty()
        }
    }

    @Test
    fun `should complete get all without value after removing lastitem of items`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1))
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { it.isEmpty() }
    }

    @Test
    fun `should complete get by key with one item after adding one item`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { value ->
            ANY_VALUE1 == value
        }
    }

    @Test
    fun `should complete get all without value after adding one item`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get by key with one item with existing key after adding all items`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY2) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { value ->
            ANY_VALUE2 == value
        }
    }

    @Test
    fun `should complete get all with all items after adding all items`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
        }
    }

    @Test
    fun `should complete get by key without a value after removing that item`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value after removing all existing items`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun `should complete get all with all values after adding all items and add another item`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver2 = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
            .flatMap { rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE5) }
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
            values.contains(ANY_VALUE4)
            values.contains(ANY_VALUE5)
        }
    }

    @Test
    fun `should complete get by key with all values with non existent key after adding all items`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
            .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
            .test()

        testObserver.onComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll().test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { values ->
            values.contains(ANY_VALUE1)
            values.contains(ANY_VALUE2)
            values.contains(ANY_VALUE3)
            values.contains(ANY_VALUE4)
        }
    }

    // Invalid cache

    @Test
    fun `should complete get by key without value when key does not exist and cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get by key without value when there is no items and cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value when key is null and cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value when key is empty and cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf())
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all with an emptylist after removing last item`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnValidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1))
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(1)
        testObserver2.assertValue { it.isEmpty() }
    }

    @Test
    fun `should complete get by key without value after adding one item because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value after adding one item because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get by key without value after adding all items because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMap { rxInMemoryCacheDataSource.getByKey(ANY_KEY2) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value after adding all items because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)
    }

    @Test
    fun `should complete get by key without value after removing an item because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE1)
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteByKey(ANY_KEY1) }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getByKey(ANY_KEY1)
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value after removing all items because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3))
            .flatMapCompletable { rxInMemoryCacheDataSource.deleteAll() }
            .test()

        testObserver.assertComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll()
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun `should complete get all without value after adding an item after adding all items because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver2 = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
            .flatMap { rxInMemoryCacheDataSource.addOrUpdate(ANY_VALUE5) }
            .flatMap { rxInMemoryCacheDataSource.getAll() }
            .test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    @Test
    fun `should complete get by key without value with non existing key after adding all items because cache is not valid`() {
        rxInMemoryCacheDataSourceMother.setupCachePolicyToReturnInvalidData()
        val rxInMemoryCacheDataSource: RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
            rxInMemoryCacheDataSourceMother.getRxInMemoryCacheDataSource()

        val testObserver = rxInMemoryCacheDataSource.replaceAll(listOf(ANY_VALUE1, ANY_VALUE2, ANY_VALUE3, ANY_VALUE4))
            .flatMap { rxInMemoryCacheDataSource.getByKey(NONEXISTENT_KEY) }
            .test()

        testObserver.onComplete()
        testObserver.assertValueCount(0)

        val testObserver2 = rxInMemoryCacheDataSource.getAll().test()

        testObserver2.assertComplete()
        testObserver2.assertValueCount(0)
    }

    companion object {
        val ANY_KEY1 = AnyRepositoryKey(1)
        val ANY_KEY2 = AnyRepositoryKey(2)
        val ANY_KEY3 = AnyRepositoryKey(3)
        val ANY_KEY4 = AnyRepositoryKey(4)
        val ANY_KEY5 = AnyRepositoryKey(5)
        val NONEXISTENT_KEY = AnyRepositoryKey(99)
        val ANY_VALUE1 = AnyRepositoryValue(ANY_KEY1)
        val ANY_VALUE2 = AnyRepositoryValue(ANY_KEY2)
        val ANY_VALUE3 = AnyRepositoryValue(ANY_KEY3)
        val ANY_VALUE4 = AnyRepositoryValue(ANY_KEY4)
        val ANY_VALUE5 = AnyRepositoryValue(ANY_KEY5)
    }
}