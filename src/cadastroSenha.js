import { StatusBar } from 'expo-status-bar';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet, Alert} from 'react-native';
  import React,{useState, useEffect} from 'react';

export default function App({navigation}) {
	const { item } = navigation;

    function submitForm () {
		Alert.alert(item);

        /*useEffect(() => {
            fetch('localhost:8080/api/usuario/registrarCliente', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
                    'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "nome" : "Teste insercao API",
                    "cpf": "77669819049",
                    "uf": "PR"
                })
            })
            .then((response) => {   
                if(!response.ok){
                    Alert.alert("Mensagem: " + response.status) 
                }
                return response.json()
            })
            .then((json) => setData(json.dados))
            .catch((error) => {
                console.error(error)})
        }, []);*/
    }
	
  return (
    <KeyboardAvoidingView style={styles.background}>
			<View style={styles.container}>

				<TextInput style={styles.input}
                    placeholder="Rua"
                    autoCorrect={false}
                />
				<TextInput style={styles.input}
                    placeholder="Numero"
                    autoCorrect={false}
                />
                <TextInput style={styles.input}
                    placeholder="UF"
                    autoCorrect={false}
                />
                <TextInput style={styles.input}
                    placeholder="Cidade"
                    autoCorrect={false}
                />
				<TextInput style={styles.input}
                    placeholder="Crie sua senha"
                    autoCorrect={false}
                />
				<TextInput style={styles.input}
                    placeholder="Confirme sua senha"
                    autoCorrect={false}
                />
				
                <TouchableOpacity style={styles.btnProximo} onPress={submitForm()}>
					<Text style={styles.textProximo}>Próximo</Text>
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
		// flex:1,
		// justifyContent: 'center'
	},
	container:{
		//flex:1,
		alignItems: 'center',
		//justifyContent: 'center',
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
    btnProximo:{
        backgroundColor: '#35AAFF',
		width: '90%',
		height: 45,
		alignItems: 'center',
		justifyContent: 'center',
		borderRadius: 7
    },
    textProximo:{
        color: '#FFF',
		fontSize: 18
    }
})