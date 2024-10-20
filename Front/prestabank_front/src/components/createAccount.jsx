//Importaciones
import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

const CreateAccount = () => {
    return(
        <div>
            <h1>Crea un cuenta</h1>
            <h3>Porfavor ingrese un correo y una contraseña</h3>

            <Box
            component="form"
            sx={{ '& > :not(style)': { m: 1, width: '25ch' } }}
            noValidate
            autoComplete="off"
          >
            <TextField id="Email" label="Correo" variant="outlined" />
            <TextField id="Password" label="Contraseña" variant="outlined" />
            </Box>

        </div>
    );
}

export default CreateAccount;