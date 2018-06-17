package stars_near_earth;

import cs_1c.*;

public class SNE_Analyzer extends  StarNearEarth
{
   private double x, y, z;
   private double rightAscensionRad, declinationRad;
   private final double LIGHT_YEAR_CONSTANT = 3.262;

   // constructors
   public SNE_Analyzer() {
      super();
      x = y = z = 0;
   }

   public SNE_Analyzer( StarNearEarth sne ) {
      setRank(sne.getRank());
      setNameCns(sne.getNameCns());
      setNumComponents(sne.getNumComponents());
      setNameLhs(sne.getNameLhs());
      setRAsc(sne.getRAsc());
      setDec(sne.getDec());
      setPropMotionMag(sne.getPropMotionMag());
      setPropMotionDir(sne.getPropMotionDir());
      setParallaxMean(sne.getParallaxMean());
      setParallaxVariance(sne.getParallaxVariance());
      SetBWhiteDwarfFlag(sne.getWhiteDwarfFlag());
      setSpectralType(sne.getSpectralType());
      setMagApparent(sne.getMagApparent());
      setMagAbsolute(sne.getMagAbsolute());
      setMass(sne.getMass());
      setNotes(sne.getNotes());
      setNameCommon(sne.getNameCommon());
      calcCartCoords();
   }

   // accessors
   double getX() { return x; }
   double getY() { return y; }
   double getZ() { return z; }


   public void calcCartCoords() {

      // Calculate the distance in light years time
      double lightYears = LIGHT_YEAR_CONSTANT/getParallaxMean();

      // Convert the RA and DEC to radians
      double rightAscensionRad = getRAsc() * Math.PI/180;
      double declinationRad = getDec() * Math.PI/180;

      // Combine these three conversion to represent x, y & z
      x = lightYears * Math.cos(declinationRad) * Math.cos(rightAscensionRad);
      y = lightYears * Math.cos(declinationRad) * Math.sin(rightAscensionRad);
      z = lightYears * Math.sin(declinationRad);
   }

   public String coordToString() {
      String cartesianCoordination;
      cartesianCoordination = ", (" + x + ", " + y + ", " + z + ")";
      return cartesianCoordination;
   }

}