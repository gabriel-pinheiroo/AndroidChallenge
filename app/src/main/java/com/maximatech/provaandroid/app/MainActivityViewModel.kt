package com.maximatech.provaandroid.app

import androidx.lifecycle.ViewModel
import com.maximatech.provaandroid.R
import com.maximatech.provaandroid.presentation.designSystem.components.bottomBar.BottomBarMenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel : ViewModel() {

    private val _menu = MutableStateFlow(setOf<BottomBarMenuItem>())
    val menu = _menu.asStateFlow()

    init {
        setMenuDefaults()
    }

    private fun setMenuDefaults() {
        val menuItems = buildSet {
            add(
                BottomBarMenuItem(
                    name = "Usuários",
                    route = "clients",
                    activeIcon = R.drawable.ic_maxima_pessoa_ativo,
                    inactiveIcon = R.drawable.ic_maxima_pessoa_inativo,
                    isSelected = true
                )
            )
            add(
                BottomBarMenuItem(
                    name = "Hist. Pedidos",
                    route = "orders",
                    activeIcon = R.drawable.ic_maxima_historico_pedidos_ativo,
                    inactiveIcon = R.drawable.ic_maxima_historico_pedidos_inativo
                )
            )
        }

        _menu.update { menuItems }
    }

    fun onMenuSelected(item: BottomBarMenuItem) {
        _menu.update { currentMenu ->
            currentMenu.map { menuItem ->
                menuItem.copy(isSelected = menuItem.route == item.route)
            }.toSet()
        }
    }
}