class Main {

  static void drawBoard (String [][]board) {
    int height = board.length;
    int width = height * 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.out.print(board[i][j]);
      }
       System.out.println("");
    }
    }

  static String[][] emptyBoard (int height ,int width) {
    String [][] b = new String [height] [width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        b[i][j] = " ";
      }
    }
    return b;
  }

  static void setSquare (String [][] board, int x, int y) {
    String piece = "#";
    board[y][x] = piece;
  }

  static void deadSquare (String [][] board, int x, int y) {
    String piece = " ";
    board[y][x] = piece;
  }

  static int checkNeighbors (String [][] board, int i, int j){
    String checkSurroundingsUp = board[i][j-1];
    String checkSurroundingsDown = board[i][j+1];
    String checkSurroundingsLeft = board[i-1][j];
    String checkSurroundingsRight = board[i+1][j];
    String checkSurroundingsUpLeft = board[i-1][j-1];
    String checkSurroundingsUpRight = board[i+1][j-1];
    String checkSurroundingsDownLeft = board[i-1][j+1];
    String checkSurroundingsDownRight = board[i+1][j+1];
    int result = 0;
    if (checkSurroundingsUp == "#"){
      result++;
    }
    if (checkSurroundingsDown == "#"){
      result++;
    }
    if (checkSurroundingsLeft == "#"){
      result++;
    }
    if (checkSurroundingsRight == "#"){
      result++;
    }
    if (checkSurroundingsUpLeft == "#"){
      result++;
    }
    if (checkSurroundingsUpRight == "#"){
      result++;
    }
    if (checkSurroundingsDownLeft == "#"){
      result++;
    }
    if (checkSurroundingsDownRight == "#"){
      result++;
    }
    return result;
  }

  static void makeGliderGun (String [][] board, int x, int y) {
    setSquare(board, x+26, y+1);
    setSquare(board, x+26, y+2);
    setSquare(board, x+26, y+6);
    setSquare(board, x+26, y+7);
    setSquare(board, x+24, y+2);
    setSquare(board, x+24, y+6);
    setSquare(board, x+23, y+3);
    setSquare(board, x+23, y+5);
    setSquare(board, x+22, y+3);
    setSquare(board, x+22, y+5);
    setSquare(board, x+22, y+4);
    setSquare(board, x+23, y+4);
    setSquare(board, x+36, y+3);
    setSquare(board, x+36, y+4);
    setSquare(board, x+37, y+3);
    setSquare(board, x+37, y+4);
    setSquare(board, x+19, y+6);
    setSquare(board, x+18, y+5);
    setSquare(board, x+18, y+6);
    setSquare(board, x+18, y+7);
    setSquare(board, x+17, y+4);
    setSquare(board, x+17, y+8);
    setSquare(board, x+16, y+6);
    setSquare(board, x+15, y+3);
    setSquare(board, x+15, y+9);
    setSquare(board, x+14, y+3);
    setSquare(board, x+14, y+9);
    setSquare(board, x+13, y+4);
    setSquare(board, x+13, y+8);
    setSquare(board, x+12, y+5);
    setSquare(board, x+12, y+6);
    setSquare(board, x+12, y+7);
    setSquare(board, x+2, y+5);
    setSquare(board, x+2, y+6);
    setSquare(board, x+3, y+5);
    setSquare(board, x+3, y+6);
  }

  static void makeGlider (String [][] board, int x, int y) {
    setSquare(board, x, y);
    setSquare(board, x+1, y+1);
    setSquare(board, x+2, y+1);
    setSquare(board, x+1, y+2);
    setSquare(board, x+2, y);
  }

  public static void main(String[] args) {
    int height = 80;
    int width = height * 2;
    String [][] board = emptyBoard(height, width);


    makeGliderGun(board, 2, 2);
    makeGliderGun(board, 52, 2);
    
    // makeGlider(board, 9, 9);
    // makeGlider(board, 19, 19);
    // makeGlider(board, 1, 1);
    // makeGlider(board, 5, 5);
    // makeGlider(board, 15, 5);
    // makeGlider(board, 5, 15);

    // for (int i = 0; i < 200; i++) { 
    //     setSquare(board, ((int) (Math.random() * (width-2))+1), ((int) (Math.random() * (height-2))+1));   
    // }
    while(true) {

      String [][] markSquare = new String [height][width];

      for (int i = 1; i < height-1; i++){
        for (int j = 1; j <  width-1; j++) {
          int result = checkNeighbors(board, i, j);
          if (result <= 1 || result >= 4) {
              markSquare[i][j] = " ";
          }
          else if (result == 3) {
              markSquare[i][j] = "#";
          } else {
            markSquare[i][j] = board[i][j];
          }
        }
      }

      for (int i = 1; i < height-1; i++){
        for (int j = 1; j <  width-1; j++) {
          board[i][j] = markSquare[i][j];
        }
      }
      
      System.out.print("\033[H\033[2J");  
      System.out.flush(); 
      drawBoard(board);
      //try{Thread.sleep(150);}catch(InterruptedException e){System.out.println(e);}  
    }
  }
}