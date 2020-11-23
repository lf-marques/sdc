import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { useState } from 'react';
import { View, KeyboardAvoidingView, Image, TextInput, 
  TouchableOpacity, Text, StyleSheet, Alert, Button} from 'react-native';

export default function App({navigation}) {
    
    const [nome, setNome]                       = useState([])
    const [cpf, setCpf]                         = useState([])
    const [dataNascimento, setDataNascimento]   = useState([])
    const [sexo, setSexo]                       = useState([])
    const [email, setEmail]                     = useState([])
    const [rg, setRg]                           = useState([])
    const [rua, setRua]                         = useState([])
    const [numero, setNumero]                   = useState([])
    const [cidade, setCidade]                   = useState([])
    const [uf, setUf]                           = useState([])
    const [senha, setSenha]                     = useState([])

    const request = () => {
        return fetch('http://localhost:8080/api/usuario/registrarCliente', {
                method: 'POST',
                headers: {
                    'Accept' : 'application/json',
                    'Content-type': 'application/json'
                },
                body:JSON.stringify({
                    "nome": nome,
                    "cpf": cpf,
                    "senha": senha,
                    "tipo": "0",
                    "cliente": {
                        "sexo": sexo,
                        "dataNascimento": dataNascimento,
                        "email": email,
                        "rg": rg,
                        "endereco": {
                            "rua": rua,
                            "numero": numero,
                            "uf": uf,
                            "cidade": cidade
                        }
                    }
                })
        }).then((response) => {   
            if(!response.ok){
                Alert.alert("Mensagem: " + response.status) 
            }
            return response.json()
        })
        .then((json) => {
            if(json.mensagem == "Cadastro realizado com sucesso!") {
                Alert.alert("Cadastro realizado, por favor faça o login."); 
                navigation.navigate('Login');
            }
        })
        .catch((error) => {
            console.error(error)})
      };


  return (
        <KeyboardAvoidingView style={styles.background}>

        <View style={styles.linhainteira}>
			<TextInput style={styles.inputtelainteira}
                placeholder='Nome Completo'
                onChangeText={nome => setNome(nome)}
			/>
		</View>
		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='63%' backgroundColor='#FFF'
				placeholder='CPF'
				keyboardType='numeric'
                onChangeText={cpf => setCpf(cpf)}
			/>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Data de Nascimento'
				keyboardType='numeric'
                onChangeText={dataNascimento => setDataNascimento(dataNascimento)}
			/>
		</View>
		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='50%' backgroundColor='#FFF'
                placeholder='E-mail'
                onChangeText={email => setEmail(email)}
			/>
			<TextInput style={styles.input} width='21%' backgroundColor='#FFF'
				placeholder='Sexo'
                onChangeText={sexo => setSexo(sexo)}
			/>
			<TextInput style={styles.input} width='22%' backgroundColor='#FFF'
				placeholder='RG'
				keyboardType='numeric'
                onChangeText={rg => setRg(rg)}
			/>
		</View>
		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='63%' backgroundColor='#FFF'
				placeholder='Rua'
                onChangeText={rua => setRua(rua)}
			/>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='Numero'
                onChangeText={numero => setNumero(numero)}
			/>		
		</View>
		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='63%' backgroundColor='#FFF'
				placeholder='Cidade'
                onChangeText={cidade => setCidade(cidade)}
			/>
			<TextInput style={styles.input} width='30%' backgroundColor='#FFF'
				placeholder='UF'
                onChangeText={uf => setUf(uf)}
			/>		
		</View>
		<View style={styles.nomeplaca}>
			<TextInput style={styles.input} width='46%' backgroundColor='#FFF'
                placeholder='Senha'
                onChangeText={senha => setSenha(senha)}
			/>
		</View>
        <Button
            title="Finalizar Cadastro"
            onPress={request}
        />
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