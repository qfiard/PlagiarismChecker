class Convert {
  
  static void convert (int Y, int U, int V, int[]p, int idx) {
    int R =
      (9535*Y + 13074*V -148464) >> 13;
    int G =
      (9535*Y - 6660*V - 3203*U -148464) >> 13;
    int B =
      (9535*Y + 16531*U -148464) >> 13;
    if (R > 255)
      R = 255;
    if (R < 0)
      R = 0;
    if (G > 255)
      G = 255;
    if (G < 0)
      G = 0;
    if (B > 255)
      B = 255;
    if (B < 0)
      B = 0;
    p[idx] = 0xff000000 | (R << 16) | (G << 8) | B;
  }

}
