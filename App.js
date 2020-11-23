import { StatusBar } from 'expo-status-bar';
import React from 'react';
import { NavigationContainer} from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import Login from './src/login';
import Cadastro from './src/cadastro1';
import CadastroSenha from './src/cadastroSenha';
import Menu from './src/menu';
import Pagamento from './src/pagamento';
import LocalizaAbaste from './src/localizaAbaste';
import Cartao from './src/cartao';

const Stack = createStackNavigator();

export default function App(props){
	return(
		<NavigationContainer>
			<Stack.Navigator>
				
				<Stack.Screen name="Login" component={Login}/>
				<Stack.Screen name="Cadastro" component={Cadastro}/>
				<Stack.Screen name="CadastroSenha" component={CadastroSenha}/>
				<Stack.Screen name="Menu" component={Menu}/>
				<Stack.Screen name="Pagamento" component={Pagamento}/>
				<Stack.Screen name="LocalizaAbaste" component={LocalizaAbaste}/>
				<Stack.Screen name="Cartao" component={Cartao}/>

			</Stack.Navigator>
		</NavigationContainer>
	);
}