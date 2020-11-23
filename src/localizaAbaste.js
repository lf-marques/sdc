import { StatusBar } from 'expo-status-bar';
import React,{useState} from 'react';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet, FlatList, Alert} from 'react-native';

export default function App(props) {
	const [idAbastecimento, setIdAbastecimento] = useState([])
	const [abastecimento, setAbastecimento] = useState([])

	const carregarAbastecimento = () => {
		return fetch('http://localhost:8080/api/abastecimento/buscarPorId', {
                method: 'POST',
				headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "id": idAbastecimento
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			console.log(json);
			setAbastecimento(json.dados);
        })
        .catch((error) => {
            console.error(error)})
	};

	const confirmarAbastecimento= () => {
		return fetch('http://localhost:8080/api/abastecimento/salvar', {
                method: 'POST',
				headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "cartaoId": abastecimento.cartaoId,
					"confirmacaoAbastecimento": 1,
					"combustivelId": abastecimento.combustivelId,
					"valor": abastecimento.valor,
					"confirmacaoPagamento": 1,
					"placa": abastecimento.placa
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			props.navigation.navigate('Menu');
        })
        .catch((error) => {
            console.error(error)})
	}

  return (
	<KeyboardAvoidingView style={styles.background}>

		<View style={styles.nomeplaca}>
			<TextInput 
				style={styles.input} 
				width='70%' 
				backgroundColor='#FFF'
				onChangeText={idAbastecimento => setIdAbastecimento(idAbastecimento)}
			/>
			<TouchableOpacity style={styles.btnpesqyusar} onPress={carregarAbastecimento}>
				<Text style={styles.txtpesquisarr}>ಠ_ಠ</Text>
			</TouchableOpacity>

		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Placa'
				value={abastecimento.id > 0 ? abastecimento.placa : null}
			/>
		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Combustivel'
				value={abastecimento.id > 0 ? abastecimento.combustivelId : null}
			/>
		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Valor'
				value={abastecimento.id > 0 ? abastecimento.valor : null}
			/>
		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Status Pagamento'
				value={abastecimento.id > 0 ? (abastecimento.confirmacaoPagamento  == 1 ? 'Pago' : 'Pendente') : null}
			/>
		</View>

		<TouchableOpacity style={abastecimento.confirmacaoAbastecimento == 0 ? styles.btnAbastecer : styles.dNone} onPress={confirmarAbastecimento}>
			<Text style={styles.textAbastecer}>Confirmar abastecimento</Text>
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
	input:{
        marginBottom:15,
		color: '#222',
		fontSize: 17,
		borderRadius: 7,
		padding: 10,
	},
	btnAbastecer:{
		width:'85%',
		height: 45,
        backgroundColor: '#35AAFF',
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 7
	},
	btnpesqyusar:{
		width:'20%',
		height: 68,
		borderRadius: 7
    },
    textAbastecer:{
        color: '#FFF',
		fontSize: 18
	},
	txtpesquisarr:{
		color: '#FFF',
		justifyContent: 'center',
		fontSize: 30
	},
	dNone: {
		display: "none"
	}
})