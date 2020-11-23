import { StatusBar } from 'expo-status-bar';
import React,{useState, useEffect} from 'react';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet} from 'react-native';

export default function Menu (props) {
	const [dadosCliente, setDadosCliente] = useState([])
	const [tipoUsuario, setTipoUsuario] = useState([])
	
	useEffect(() => {
		return fetch('http://localhost:8080/api/cliente/buscarPorCpf', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
					'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "cpf": props.route.params.data.cpf
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
			}
            return response.json()
        })
        .then((json) => {
			setDadosCliente(json.dados);
        })
        .catch((error) => {
            console.error(error)})
	}, []);
	  
  return (
    <KeyboardAvoidingView style={styles.background}>

			<View style={styles.container1}>
              
                <TouchableOpacity style={dadosCliente.id > 0  ? styles.btnProximo : styles.dNone} onPress={() => props.navigation.navigate('Pagamento', {dadosCliente})}>
					<Text style={styles.textProximo}>ABASTECER</Text>
				</TouchableOpacity>

				{/* criar tela de cadastro de cartões */}
                <TouchableOpacity style={dadosCliente.id > 0  ? styles.btnProximo : styles.dNone} onPress={() => props.navigation.navigate('Cartao', {dadosCliente})}> 
					<Text style={styles.textProximo}>CARTÕES</Text>
				</TouchableOpacity>           
            </View>

			<View style={styles.container2}>
              
                {/* <TouchableOpacity style={styles.btnProximo}>
					<Text style={styles.textProximo}>LISTAR</Text>
				</TouchableOpacity> */}

                <TouchableOpacity style={dadosCliente.id == 0  ? styles.dNone : styles.btnProximo} onPress={() => props.navigation.navigate('LocalizaAbaste')}>
					<Text style={styles.textProximo}>USER</Text>
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
		backgroundColor: '#191919',
	},
	containerLogo:{
		flex:1,
		justifyContent: 'center'
	},
	container1:{
		flex:1,
		flexDirection:'row',
		alignItems: 'center',
		justifyContent: 'space-evenly',
		width: '90%'
	},
	container2:{
		flex:1,
		flexDirection:'row',
		alignItems: 'baseline',
		justifyContent: 'space-evenly',
		width: '90%',
	},
	btnProximo:{
		backgroundColor: '#FFDEAD',
		borderRadius: 7,
		justifyContent: 'center',
		alignItems: 'center',
		width: '45%',
		height: '50%'
	},
    textProximo:{
        color: '#000080',
		fontSize: 18,
		fontWeight: 'bold'
	},
	dNone: {
		display: "none"
	}
})