package com.ateca.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ateca.domain.constants.NavigationConstants
import com.ateca.ui.navigation.Animations.enterTransition
import com.ateca.ui.navigation.Animations.exitTransition
import com.ateca.ui.navigation.Screen
import com.ateca.ui.screens.note_screen.NoteScreen
import com.ateca.ui.screens.note_screen.veiwmodel.NoteViewModel
import com.google.accompanist.navigation.animation.composable

/**
 * Modified by dronpascal on 21.05.2022.
 */
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addNoteDetailed(
    navController: NavHostController,
) {
    composable(
        route = Screen.Note.route,
        arguments = listOf(navArgument(NavigationConstants.NOTE_ID_ARGUMENT_KEY) {
            type = NavType.StringType
        }),
        enterTransition = {
            enterTransition
        },
        exitTransition = {
            exitTransition
        }
    ) {
        val viewModel: NoteViewModel = hiltViewModel()
        NoteScreen(
            viewModel = viewModel,
            navController = navController
        )
    }
}