import { NavigationContainer } from '@react-navigation/native';
import { StatusBar } from 'expo-status-bar';
import React,{useState} from 'react';
import {View, KeyboardAvoidingView, Image, TextInput, 
		TouchableOpacity, Text, StyleSheet} from 'react-native';

export default function Login (props){
	const [cpf, setCpf] 	  = useState([])
	const [senha, setSenha]   = useState([])

	const request = () => {
        return fetch('http://localhost:8080/auth', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
                    'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "cpf": cpf,
                    "senha": senha
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
			
            return response.json()
        })
        .then((json) => {
			if(json.dados.token != "") {
				props.navigation.navigate('Menu', {data: {cpf: cpf, token: json.dados.token}});
			}
        })
        .catch((error) => {
            console.error(error)})
	  };

	return(
		<KeyboardAvoidingView style={styles.background}>
			<View style={styles.container}>
				<TextInput style={styles.input}
					placeholder="CPF"
					keyboardType='numeric'
					autoCorrect={false}
					onChangeText={cpf => setCpf(cpf)}
				/>

				<TextInput style={styles.input}
					placeholder="Senha"
					autoCorrect={false}
					onChangeText={senha => setSenha(senha)}
				/>

				<TouchableOpacity style={styles.btnAcessar} onPress={request}>
					<Text style={styles.textAcessar}>Acessar</Text>
				</TouchableOpacity>

				<TouchableOpacity style={styles.btnCadastrar} onPress={() => navigation.navigate('Cadastro')}>
					<Text style={styles.textCadastrar}>Novo por aqui? Cadastre-se</Text>
				</TouchableOpacity>

			</View>
		</KeyboardAvoidingView>
	);
}

const styles = StyleSheet.create({
	background:{
		flex:1,
		alignItems:	'center',
		justifyContent: 'center',
		backgroundColor: '#191919'
	},
	containerLogo:{
		flex:1,
		justifyContent: 'center'
	},
	container:{
		flex:3,
		alignItems: 'center',
		justifyContent: 'center',
		width: '90%',
	},
	input:{
		backgroundColor: '#FFF',
		width: '90%',
        marginBottom:15,
		color: '#222',
		fontSize: 17,
		borderRadius: 7,
		padding: 10,
	},
	btnAcessar:{
		backgroundColor: '#35AAFF',
		width: '90%',
		height: 45,
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 7
	},
	textAcessar:{
		color: '#FFF',
		fontSize: 18
	},
	btnCadastrar:{
		marginTop: 10,
	},
	textCadastrar:{
		color: '#FFF'
	}
});