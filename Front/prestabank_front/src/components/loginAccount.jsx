// Importaciones
import * as React from 'react';
import { useState } from "react";
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button'; // Importar el botón
import AccountCircle from '@mui/icons-material/AccountCircle';
import { useNavigate } from 'react-router-dom';
import userService from '../services/user.service';

const LoginAccount = () => {
    const navigate = useNavigate();

    const enterAccount = () => {
        navigate('/'); // Redirige a la ruta para crear cuenta
    }

    // Definición de estados
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    // Función para manejar el envío del formulario (puedes implementarlo más tarde)
    const handleLogin = async (event) => {
        event.preventDefault();
        // Aquí puedes agregar la lógica de inicio de sesión usando userService
    };

    return ( // Asegúrate de retornar el JSX aquí
        <div>
            <h1>Ingrese una cuenta</h1>
            <h3>Por favor ingrese los datos</h3>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>

            <Box
                component="form"
                onSubmit={handleLogin} // Maneja el envío del formulario
                sx={{ '& > :not(style)': { m: 1, width: '30ch' } }}
                noValidate
                autoComplete="off"
            >

                <AccountCircle sx={{ color: 'action.active', mr: 1, my: 0.2 }} />

                <TextField
                    id="input-email"
                    label="Correo"
                    variant="outlined"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <TextField
                    id="input-password"
                    label="Contraseña"
                    type="password"
                    variant="outlined"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <br />
                <br />
                <Button 
                    variant="contained" 
                    type="submit" // Asegúrate de que el botón envíe el formulario
                    sx={{ mt: 2 }} // Añade un margen superior para separación
                >
                    Iniciar sesión
                </Button>
            </Box>
        </div>
    );
}

export default LoginAccount;
