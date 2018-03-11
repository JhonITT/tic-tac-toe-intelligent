/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.tictactoeintelligent.controllers;

import com.mycompany.tictactoeintelligent.models.Board;
import com.mycompany.tictactoeintelligent.models.Player;
import com.mycompany.tictactoeintelligent.models.State;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author jhon
 */
public class MainController {

    private JButton casilla00;
    private JButton casilla01;
    private JButton casilla02;
    private JButton casilla10;
    private JButton casilla11;
    private JButton casilla12;
    private JButton casilla20;
    private JButton casilla21;
    private JButton casilla22;
    private Board estadoActual;

    public MainController(Board estadoActual, JButton casilla00, JButton casilla01, JButton casilla02, JButton casilla10, JButton casilla11, JButton casilla12, JButton casilla20, JButton casilla21, JButton casilla22) {
        this.estadoActual = estadoActual;
        this.casilla00 = casilla00;
        this.casilla01 = casilla01;
        this.casilla02 = casilla02;
        this.casilla10 = casilla10;
        this.casilla11 = casilla11;
        this.casilla12 = casilla12;
        this.casilla20 = casilla20;
        this.casilla21 = casilla21;
        this.casilla22 = casilla22;
    }

    public void humanPlay(int row, int column) {
        if (estadoActual.nextMove(row, column) != null) {
            estadoActual = estadoActual.nextMove(row, column);
            pintarTablero(estadoActual);
            
            if (!estadoActual.getState().equals(State.UNDEFINED)) {
                JOptionPane.showMessageDialog(null, estadoActual.getState());
                estadoActual.beginAgain();
                pintarTablero(estadoActual);
            }
        } else {
            return;
        }
        //--------------------------------------
    }

    public void computerPlay() {
        //--------------------------------------
        if (estadoActual.getMoveds() == 0) {
            double valor = Math.random() * 100;
            if (valor <= 20) {
                estadoActual = estadoActual.nextMove(0, 0);
            } else if (valor > 20 && valor <= 40) {
                estadoActual = estadoActual.nextMove(0, 2);
            } else if (valor > 40 && valor <= 60) {
                estadoActual = estadoActual.nextMove(2, 0);
            } else if (valor > 60 && valor <= 80) {
                estadoActual = estadoActual.nextMove(2, 2);
            } else if (valor > 80 && valor <= 100) {
                estadoActual = estadoActual.nextMove(1, 1);
            }
            pintarTablero(estadoActual);
        } else if (estadoActual.getMoveds() == 1) {
            if (estadoActual.nextMove(1, 1) != null) {
                estadoActual = estadoActual.nextMove(1, 1);
                pintarTablero(estadoActual);
            } else {
                double valor = Math.random() * 100;
                if (valor <= 25) {
                    estadoActual = estadoActual.nextMove(0, 0);
                } else if (valor > 25 && valor <= 50) {
                    estadoActual = estadoActual.nextMove(0, 2);
                } else if (valor > 50 && valor <= 75) {
                    estadoActual = estadoActual.nextMove(2, 0);
                } else if (valor > 75 && valor <= 100) {
                    estadoActual = estadoActual.nextMove(2, 2);
                }

                pintarTablero(estadoActual);
            }

        } else if (estadoActual.nextForcedMovements().size() > 0) {

            if (estadoActual.nextForcedMovements().size() > 1) {
                if (estadoActual.nextForcedMovements().get(0).getWeight(estadoActual.getTurn()) > estadoActual.nextForcedMovements().get(1).getWeight(estadoActual.getTurn())) {
                    estadoActual = estadoActual.nextForcedMovements().get(0);
                } else {
                    estadoActual = estadoActual.nextForcedMovements().get(1);
                }
            } else {
                estadoActual = estadoActual.nextForcedMovements().get(0);
            }

            pintarTablero(estadoActual);

        } else {

            int max = 0;
            Board mejorCamino = estadoActual.nextMovements().get(0);

            for (Board proximaJugada : estadoActual.nextMovements()) {
                for (Board camino : proximaJugada.nextForcedMovements()) {
                    if (camino.roadWeight(camino) > max) {
                        mejorCamino = proximaJugada;
                        max = camino.roadWeight(camino);
                    }
                }
            }

            estadoActual = mejorCamino;
            pintarTablero(estadoActual);

        }
        if (!estadoActual.getState().equals(State.UNDEFINED)) {
            JOptionPane.showMessageDialog(null, estadoActual.getState());
            estadoActual.beginAgain();
            pintarTablero(estadoActual);
        }
    }

    public void jugar(int fila, int colunna) {
        humanPlay(fila, colunna);
        computerPlay();
    }

    public void pintarTablero(Board estadoActual) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                pintarCasilla(i, j, estadoActual.getBoard()[i][j]);
            }
        }
    }

    public void pintarCasilla(int fila, int colunna, Player marca) {
        switch (fila) {
            case 0:
                switch (colunna) {
                    case 0:
                        casilla00.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 1:
                        casilla01.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 2:
                        casilla02.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;

                }
                break;
            case 1:
                switch (colunna) {
                    case 0:
                        casilla10.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 1:
                        casilla11.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 2:
                        casilla12.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;

                }
                break;
            case 2:
                switch (colunna) {
                    case 0:
                        casilla20.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 1:
                        casilla21.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;
                    case 2:
                        casilla22.setText(marca.equals(Player.NULL) ? "" : (marca.equals(Player.X) ? "X" : "O"));
                        break;

                }
                break;

        }
    }

}
