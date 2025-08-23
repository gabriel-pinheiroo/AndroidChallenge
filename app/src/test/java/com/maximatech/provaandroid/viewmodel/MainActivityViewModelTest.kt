package com.maximatech.provaandroid.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.app.MainActivityViewModel
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainActivityViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `menu items should have correct properties`() = runTest {
        val menuItems = viewModel.menu.first()

        menuItems.forEach { item ->
            assertNotNull(item.name)
            assertNotNull(item.route)
            assertTrue(item.name.isNotEmpty())
            assertTrue(item.route.isNotEmpty())
            assertTrue(item.activeIcon != 0)
            assertTrue(item.inactiveIcon != 0)
        }
    }

    @Test
    fun `only one menu item should be selected at a time`() = runTest {
        val ordersItem = BottomBarMenuItem(
            name = "Hist. Pedidos",
            route = "orders",
            activeIcon = R.drawable.ic_maxima_historico_pedidos_ativo,
            inactiveIcon = R.drawable.ic_maxima_historico_pedidos_inativo
        )

        viewModel.onMenuSelected(ordersItem)

        val menuItems = viewModel.menu.first()
        val selectedItems = menuItems.filter { it.isSelected }

        assertEquals(1, selectedItems.size)
        assertEquals("orders", selectedItems.first().route)
    }

    @Test
    fun `menu should be immutable Set`() = runTest {
        val menuItems = viewModel.menu.first()

        assertTrue(menuItems is Set)
        assertEquals(2, menuItems.size)
    }

    @Test
    fun `when viewModel is initialized, should have default menu items`() = runTest {
        val menuItems = viewModel.menu.first()

        assertEquals(2, menuItems.size)

        val clientsItem = menuItems.find { it.route == "clients" }
        val ordersItem = menuItems.find { it.route == "orders" }

        assertNotNull(clientsItem)
        assertNotNull(ordersItem)

        assertEquals("Usuários", clientsItem?.name)
        assertEquals("clients", clientsItem?.route)
        assertEquals(R.drawable.ic_maxima_pessoa_ativo, clientsItem?.activeIcon)
        assertEquals(R.drawable.ic_maxima_pessoa_inativo, clientsItem?.inactiveIcon)
        assertTrue(clientsItem?.isSelected == true) // Should be selected by default

        assertEquals("Hist. Pedidos", ordersItem?.name)
        assertEquals("orders", ordersItem?.route)
        assertEquals(R.drawable.ic_maxima_historico_pedidos_ativo, ordersItem?.activeIcon)
        assertEquals(R.drawable.ic_maxima_historico_pedidos_inativo, ordersItem?.inactiveIcon)
        assertFalse(ordersItem?.isSelected == true) // Should not be selected by default
    }

    @Test
    fun `when onMenuSelected is called with clients item, should select clients and deselect others`() =
        runTest {
            val clientsItem = BottomBarMenuItem(
                name = "Usuários",
                route = "clients",
                activeIcon = R.drawable.ic_maxima_pessoa_ativo,
                inactiveIcon = R.drawable.ic_maxima_pessoa_inativo
            )

            viewModel.onMenuSelected(clientsItem)

            val menuItems = viewModel.menu.first()
            val selectedClientsItem = menuItems.find { it.route == "clients" }
            val ordersItem = menuItems.find { it.route == "orders" }

            assertTrue(selectedClientsItem?.isSelected == true)
            assertFalse(ordersItem?.isSelected == true)
        }

    @Test
    fun `when onMenuSelected is called with orders item, should select orders and deselect others`() =
        runTest {
            val ordersItem = BottomBarMenuItem(
                name = "Hist. Pedidos",
                route = "orders",
                activeIcon = R.drawable.ic_maxima_historico_pedidos_ativo,
                inactiveIcon = R.drawable.ic_maxima_historico_pedidos_inativo
            )

            viewModel.onMenuSelected(ordersItem)

            val menuItems = viewModel.menu.first()
            val clientsItem = menuItems.find { it.route == "clients" }
            val selectedOrdersItem = menuItems.find { it.route == "orders" }

            assertFalse(clientsItem?.isSelected == true)
            assertTrue(selectedOrdersItem?.isSelected == true)
        }

    @Test
    fun `when onMenuSelected is called multiple times, should update selection correctly`() =
        runTest {
            val clientsItem = BottomBarMenuItem(
                name = "Usuários",
                route = "clients",
                activeIcon = R.drawable.ic_maxima_pessoa_ativo,
                inactiveIcon = R.drawable.ic_maxima_pessoa_inativo
            )

            val ordersItem = BottomBarMenuItem(
                name = "Hist. Pedidos",
                route = "orders",
                activeIcon = R.drawable.ic_maxima_historico_pedidos_ativo,
                inactiveIcon = R.drawable.ic_maxima_historico_pedidos_inativo
            )

            var menuItems = viewModel.menu.first()
            var selectedClientsItem = menuItems.find { it.route == "clients" }
            var selectedOrdersItem = menuItems.find { it.route == "orders" }

            assertTrue(selectedClientsItem?.isSelected == true)
            assertFalse(selectedOrdersItem?.isSelected == true)

            viewModel.onMenuSelected(ordersItem)

            menuItems = viewModel.menu.first()
            selectedClientsItem = menuItems.find { it.route == "clients" }
            selectedOrdersItem = menuItems.find { it.route == "orders" }

            assertFalse(selectedClientsItem?.isSelected == true)
            assertTrue(selectedOrdersItem?.isSelected == true)

            viewModel.onMenuSelected(clientsItem)

            menuItems = viewModel.menu.first()
            selectedClientsItem = menuItems.find { it.route == "clients" }
            selectedOrdersItem = menuItems.find { it.route == "orders" }

            assertTrue(selectedClientsItem?.isSelected == true)
            assertFalse(selectedOrdersItem?.isSelected == true)
        }

    @Test
    fun `when onMenuSelected is called with unknown route, should deselect all items`() = runTest {
        val unknownItem = BottomBarMenuItem(
            name = "Unknown",
            route = "unknown",
            activeIcon = R.drawable.ic_maxima_pessoa_ativo,
            inactiveIcon = R.drawable.ic_maxima_pessoa_inativo
        )

        val initialMenuItems = viewModel.menu.first()
        val initialClientsSelected = initialMenuItems.find { it.route == "clients" }?.isSelected
        assertEquals(true, initialClientsSelected)

        viewModel.onMenuSelected(unknownItem)

        val menuItems = viewModel.menu.first()
        val clientsSelected = menuItems.find { it.route == "clients" }?.isSelected
        val ordersSelected = menuItems.find { it.route == "orders" }?.isSelected

        assertEquals(false, clientsSelected)
        assertEquals(false, ordersSelected)
        assertEquals(2, menuItems.size)
    }
}