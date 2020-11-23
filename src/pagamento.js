import { StatusBar } from 'expo-status-bar';
import React,{useState,useEffect} from 'react';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet, Alert, Picker} from 'react-native';

export default function Pagamento (props) {
	const [combustiveis, setCombustiveis] 	= useState([])
	const [cartoes, setCartoes] 			= useState([])
	const [placa, setPlaca] 				= useState([])
	const [combustivel, setCombustivel] 	= useState([])
	const [valor, setValor] 				= useState([])
	const [cartao, setCartao] 				= useState([])
	const [idAbastecimento, setidAbastecimento] 	= useState([])

	const gerarAbastecimento = () => {
        return fetch('http://localhost:8080/api/abastecimento/salvar', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
					"cartaoId": cartao,
					"confirmacaoAbastecimento": "0",
					"confirmacaoPagamento": "0",
					"valor": valor,
					"placa": placa,
					"combustivelId": combustivel
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			if(json.dados.id != "") {
				setidAbastecimento(json.dados.id);
			}
        })
        .catch((error) => {
            console.error(error)})
	};

	const carregarCartoes = () => {
		return fetch('http://localhost:8080/api/cartao/buscarPorCliente', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "id": props.route.params.dadosCliente.id
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			console.log(json);
			setCartoes(json.dados);
        })
        .catch((error) => {
            console.error(error)})
	};

	const carregarCombustiveis = () => {
		return fetch('http://localhost:8080/api/combustivel/listar', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                }
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			console.log(json);
			setCombustiveis(json.dados);
        })
        .catch((error) => {
            console.error(error)})
	};

	if(combustiveis.length == 0) {
		carregarCombustiveis();
	}

	if(cartoes.length == 0) {
		carregarCartoes();
	}
  return (
	<KeyboardAvoidingView style={styles.background}>

		<View>
			<Text style={{color: "white"}}>Código abastecimento: {idAbastecimento != "" ? idAbastecimento : null}</Text>
		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Placa'
				onChangeText={placa => setPlaca(placa)}
			/>
		</View>

		<View style={styles.linhainteira}>
			<Picker
				style={styles.inputtelainteira}
				onValueChange={(itemValue, itemIndex) => setCombustivel(itemValue)}>
				<Picker.Item label="Selecione um combustivel" value="0" key="0"/>
				{combustiveis.map((item, index) => {
					return (<Picker.Item label={item.titulo} value={item.id} key={item.id}/>) 
				})}
			</Picker>
		</View>

		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='63%' backgroundColor='#FFF'
				placeholder='Valor'
				keyboardType='numeric'
				onChangeText={valor => setValor(valor)}
			/>
		</View>		

		<View style={styles.linhainteira}>
			<Picker
				style={styles.inputtelainteira}
				onValueChange={(itemValue, itemIndex) => setCartao(itemValue)}>
				<Picker.Item label="Selecione um cartão" value="0" key="0"/>
				{cartoes.map((item, index) => {
					return (<Picker.Item label={item.numero} value={item.id} key={item.id}/>) 
				})}
			</Picker>
		</View>

		<TouchableOpacity style={styles.btnAbastecer} onPress={gerarAbastecimento}>
			<Text style={styles.textAbastecer}>Abastecer</Text>
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