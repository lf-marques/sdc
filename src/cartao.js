import { StatusBar } from 'expo-status-bar';
import React,{useState} from 'react';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet, Alert} from 'react-native';

export default function Cartao (props) {
	const [numero, setNumero]   			= useState([])
	const [bloqueado, setBloqueado]   		= useState([])
	const [dataValidade, setDataValidade]   = useState([])

	const salvarCartao = () => {
        return fetch('http://localhost:8080/api/cartao/salvar', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "numero": numero,
					"dataValidade": dataValidade,
					"bloqueado": bloqueado,
					"clienteId": props.route.params.dadosCliente.id
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			if(json.dados.id != "") {
				props.navigation.navigate('Menu', {dadosCliente: props.route.params.dadosCliente});
			}
        })
        .catch((error) => {
            console.error(error)})
	};

  return (
	<KeyboardAvoidingView style={styles.background}>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='63%' backgroundColor='#FFF'
                placeholder='Número do cartão'
                keyboardType='numeric'
				onChangeText={numero => setNumero(numero)}
			/>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='DataValidade'
				onChangeText={dataValidade => setDataValidade(dataValidade)}
			/>
		</View>

		<View style={styles.linhainteira}>
			<TextInput style={styles.inputtelainteira}
				placeholder='Bloqueado'
				onChangeText={bloqueado => setBloqueado(bloqueado)}
			/>
		</View>

		<TouchableOpacity style={styles.btnAbastecer} onPress={salvarCartao}>
			<Text style={styles.textAbastecer}>Cadastrar</Text>
		</TouchableOpacity>

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
		flex:0.3,
		justifyContent: 'flex-start'
	},
	nomeplaca:{
		flexDirection:'row',
		alignItems: 'center',
		justifyContent: 'space-evenly',
		width: '90%'
	},
	linhainteira:{
		alignItems: 'center',
		justifyContent: 'center',
		width: '95%',
	},
	inputtelainteira:{
		backgroundColor: '#FFF',
		width: '90%',
        marginBottom:15,
		color: '#222',
		fontSize: 17,
		borderRadius: 7,
		padding: 10,
	},
	input:{
        marginBottom:15,
		color: '#222',
		fontSize: 17,
		borderRadius: 7,
		padding: 10,
	},
	btnAbastecer:{
        backgroundColor: '#35AAFF',
		width: '85%',
		height: 45,
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 7
    },
    textAbastecer:{
        color: '#FFF',
		fontSize: 18
    }
})