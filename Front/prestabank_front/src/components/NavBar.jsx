import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';

const NavBar = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        navigate('/');
    }

    return (
        <AppBar sx={{ backgroundColor: '#1565c0' }}>
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                <Typography variant="h6" component="div" sx={{ flexGrow: 0 }}>
                    Menú
                </Typography>

                <Box sx={{ display: 'flex', gap: 2 }}>
                    <Button 
                        color="inherit" 
                        onClick={() => navigate('/simular-credito')}
                        sx={{ 
                            '&:hover': { transform: 'scale(1.1)', transition: '0.3s' }
                        }}
                    >
                        Simular Crédito
                    </Button>
                    <Button 
                        color="inherit" 
                        onClick={() => navigate('/solicitar-credito')}
                        sx={{ 
                            '&:hover': { transform: 'scale(1.1)', transition: '0.3s' }
                        }}
                    >
                        Solicitar Crédito
                    </Button>
                    <Button 
                        color="inherit" 
                        onClick={() => navigate('/evaluar-credito')}
                        sx={{ 
                            '&:hover': { transform: 'scale(1.1)', transition: '0.3s' }
                        }}
                    >
                        Evaluar Crédito
                    </Button>
                    <Button 
                        color="inherit" 
                        onClick={() => navigate('/editar-credito')}
                        sx={{ 
                            '&:hover': { transform: 'scale(1.1)', transition: '0.3s' }
                        }}
                    >
                        Editar Crédito
                    </Button>
                    <Button 
                        color="inherit" 
                        onClick={handleLogout}
                        sx={{ 
                            '&:hover': { transform: 'scale(1.1)', transition: '0.3s' }
                        }}
                    >
                        Cerrar Sesión
                    </Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
}

export default NavBar;
