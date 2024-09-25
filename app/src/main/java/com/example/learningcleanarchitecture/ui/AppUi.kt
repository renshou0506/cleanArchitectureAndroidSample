package com.example.learningcleanarchitecture.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.learningcleanarchitecture.R
import com.example.learningcleanarchitecture.ui.navigation.AppNavHost
import com.example.learningcleanarchitecture.ui.theme.DeepBlue
import com.example.learningcleanarchitecture.ui.theme.LightBlue
import kotlinx.coroutines.launch

@Composable
fun AppUi(navController: NavHostController) {
    val drawState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerViewMode: DrawerViewModel = hiltViewModel()

    ModalNavigationDrawer(
        drawerState = drawState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "drawer content"
                )
            }
        }
    ) {
        val coroutineScope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }

        val showSnackBar: (String) -> Unit = {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }

        Scaffold(
            topBar = { TopAppBar(drawerState = drawState) },
            bottomBar = {
                BottomAppBar(navController = navController)
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
        ) {
            Box(
                modifier = Modifier.padding(
                    PaddingValues(
                        0.dp,
                        it.calculateTopPadding(),
                        0.dp,
                        it.calculateBottomPadding()
                    )
                )
            ) {
                // 画面下部ボタン
                AppNavHost(navController = navController, snackBar = showSnackBar)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            titleContentColor = Color.White,
            containerColor = DeepBlue
        ),
        title = {
            Text(text = "decided title")

        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open() }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.humburger),
                    contentDescription = "Open drawer",
                    tint = Color.White
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@Composable
fun BottomAppBar(navController: NavController) {
    BottomAppBar(contentPadding = PaddingValues(0.dp)) {
        BottomNavigationBar(navController = navController)
    }
}

sealed class NavigationItem(var route: String, @DrawableRes val id: Int = 0, var titleId: Int = 0) {
    data object HomeTab : NavigationItem(route = "HomeTab") {
        data object HomeScreen : NavigationItem(route = "HomeTab/HomeScreen", R.drawable.home_24px, titleId = R.string.home)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.HomeTab.HomeScreen,
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    var currentRoute by remember { mutableStateOf(NavigationItem.HomeTab.route) }

    items.forEachIndexed() { index, navigationItem ->
        if (navigationItem.route == currentRoute) {
            selectedItem = index
        }
    }
    NavigationBar(containerColor = LightBlue) {
        items.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(if (selectedItem == index) DeepBlue else LightBlue)
                    .weight(1f)
                    .clickable {
                        selectedItem = index
                        currentRoute = item.route
                        navController.navigate(route = item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = item.id),
                    contentDescription = stringResource(id = item.titleId)
                )
                Text(text = stringResource(id = item.titleId),
                    fontSize = 12.sp,
                    color = Color.White)
            }
        }
    }
}