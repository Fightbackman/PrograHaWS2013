class Brett {
  int n; // Es ist ein n x n - Brett
  boolean brett[][];  // brett[y][x]: Steht Dame auf (y,x)
  boolean fertig = false;

  public Brett(int n) {
    this.n = n;
    brett = new boolean[n][n];
    for(int i=0; i<n; i++) {
      for(int j=0; j<n; j++) {
        brett[i][j] = false;
      }
    }
  }

  public void print() {
    for(int y=0; y<n; y++) {
      for(int x=0; x<n; x++) {
        if(brett[y][x]) {
          System.out.print("Q ");
        }
        else {
          System.out.print(". ");
        }
      }
      System.out.println();
    }
  }

  boolean obenOkay(int y, int x, int dy, int dx) {
    while(0 <= y && y < n && 0 <= x && x < n) {
      if(brett[y][x]) {
        return false;
      }
      y = y + dy;
      x = x + dx;
    }
    return true;
  }

  // Kann eine Dame auf Position (y,x) gestellt werden?
  boolean safePosition(int y, int x) {
    return obenOkay(y, x, -1, 0) &&
           obenOkay(y, x, -1, -1) &&
           obenOkay(y, x, -1, +1); 
  }

  void setzeDamenAbZeile(int y) {
    if(y >= n) {
      fertig = true;
      return;
    }
    for(int x=0; x<n; x++) {
      if(safePosition(y, x)&& sprungOkay(y,x)) { 
        brett[y][x] = true;
        setzeDamenAbZeile(y+1);
        if(fertig) {
          return;
        }
        brett[y][x] = false;
      }
    }
  }
  
  boolean sprungOkay(int y, int x){
      if(y<n-2){
          if (x<n-1){
              if (brett[y+2][x+1]){
                  return false;
              }
          } 
          if (x>0){
              if (brett[y+2][x-1]){
                  return false;
              }
          }
      }
      if(y<n-1){
          if (x<n-2){
              if (brett[y+1][x+2]){
                  return false;
              }
          } 
          if (x>1){
              if (brett[y+1][x-2]){
                  return false;
              }
          }
      }
      if(y>1){
          if (x<n-1){
              if (brett[y-2][x+1]){
                  return false;
              }
          }  
          if (x>0){
              if (brett[y-2][x-1]){
                  return false;
              }
          }
      }
      if(y>0){
          if (x<n-2){
              if (brett[y-1][x+2]){
                  return false;
              }
          } 
          if (x>1){
              if (brett[y-1][x-2]){
                  return false;
              }
          }
      }
      return true;
  }
  
  
}