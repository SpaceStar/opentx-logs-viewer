package ru.spacestar.calculator_impl.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.spacestar.calculator_impl.R
import ru.spacestar.calculator_impl.ui.business.CalculatorSideEffect
import ru.spacestar.calculator_impl.ui.business.CalculatorViewModel
import ru.spacestar.calculator_impl.utils.ReactionFormatter
import ru.spacestar.core_ui.theme.LogsViewerTheme
import ru.spacestar.core_ui.view.BaseAppBarScreen

@Composable
internal fun Calculator(
    navController: NavController,
    viewModel: CalculatorViewModel
) {
    val state by viewModel.collectAsState()
    var localInput by remember { mutableStateOf(state.input) }
    val filter = remember { Regex("""[^\dA-Za-z+ ]""") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    viewModel.collectSideEffect {
        when (it) {
            CalculatorSideEffect.RequestFocus -> focusRequester.requestFocus()
            CalculatorSideEffect.HideKeyboard -> focusManager.clearFocus()
            is CalculatorSideEffect.Navigate -> navController.navigate(it.route)
        }
    }

    BaseAppBarScreen(
        navController = navController,
        isBackEnabled = false,
        appBarMenu = {
            IconButton(onClick = {
                viewModel.navigateInfo()
            }) {
                Icon(
                    imageVector = Icons.Outlined.HelpOutline,
                    contentDescription = stringResource(
                        ru.spacestar.core_feature_api.R.string.info_title
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = localInput,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions { viewModel.search() },
                visualTransformation = ReactionTransform(),
                label = { Text(stringResource(id = R.string.calc_input_hint)) },
                onValueChange = {
                    if (it.contains('\n')) {
                        viewModel.search()
                        return@OutlinedTextField
                    }
                    if (filter.containsMatchIn(it)) return@OutlinedTextField
                    localInput = it
                    viewModel.input(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            Text(
                text = state.result,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .weight(1f)
            )
            Button(
                onClick = { viewModel.search() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.calc_result_button))
            }
        }
    }
}

private class ReactionTransform : VisualTransformation {
    private val formatter by lazy { ReactionFormatter() }
    override fun filter(text: AnnotatedString): TransformedText {
        var result = text.text
        result = result.replace(' ', '+')
        result = formatter.toReaction(result)
        return TransformedText(AnnotatedString(result), OffsetMapping.Identity)
    }
}

@Preview
@Composable
private fun Preview() {
    LogsViewerTheme {
        Calculator(rememberNavController(), viewModel())
    }
}