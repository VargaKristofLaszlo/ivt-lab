package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){  

    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);

    this.ship = new GT4500(primary,secondary);
  }


  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);  
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange  
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fire_Torpedo_Single_Last_Primary_Secondary_Not_Empty() {
    //Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);  
    when(primary.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    //Act   
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertTrue(result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }


  @Test
  public void fire_Torpedo_Single_Last_Primary_Secondary_And_Primary_Are_Empty() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primary.isEmpty()).thenReturn(true);

    //Act    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertFalse(result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }


  @Test
  public void fire_Torpedo_Single_Last_Primary_Secondary_Is_Empty_Primaray_Is_Not() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    //Act    
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertTrue(result);
    verify(secondary, times(0)).fire(1);
    verify(primary, times(2)).fire(1);
  }

  @Test
  public void fire_Torpedo_Single_Last_Was_Not_Primary_Primary_Is_Not_Empty() {
    //Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertTrue(result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fire_Torpedo_Single_Last_Was_Not_Primary_Primary_Is_Empty_Secondary_Is_Not() {
    //Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertTrue(result);
    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fire_Torpedo_Single_Last_Was_Not_Primary_Primary_And_Secondary_Are_Empty() {
    //Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertFalse(result);
    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fire_Torpedo_All_Primary_Is_Empty() {
    //Arrange
    when(primary.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }

  @Test
  public void fire_Torpedo_All_Secondary_Is_Empty() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }

  @Test
  public void fire_Torpedo_All_Secondary_And_Primary_Are_Empty() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }

  @Test
  public void fire_Torpedo_All_Stores_Are_Not_Empty() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertTrue(result);
  }

  @Test
  public void fire_Torpedo_All_Stores_Are_Not_Empty_Primary_Fails_Fire() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }
  @Test
  public void fire_Torpedo_All_Stores_Are_Not_Empty_Secondary_Fails_Fire() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }

  @Test
  public void fire_Torpedo_All_Stores_Are_Not_Empty_Both_Fails_Fire() {
    //Arrange
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertFalse(result);
  }

  @Test
  public void Fire_Laser_All_Fails()
  {
    boolean result = ship.fireLaser(FiringMode.ALL);
    assertFalse(result);
  }

  @Test
  public void Fire_Laser_Single_Fails()
  {
    boolean result = ship.fireLaser(FiringMode.SINGLE);
    assertFalse(result);
  }

  @Test
  public void Creation_Test() {
    GT4500 tmp = new GT4500();

    assertNotNull(tmp);
  }
}
