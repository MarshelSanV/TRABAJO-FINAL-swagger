@PetStore
Feature: Gestión de Mascotas en Swagger PetStore

  Scenario Outline: Crear una mascota exitosamente
    Given que el actor establece el endpoint de PetStore
    When el actor registra una mascota con nombre "<nombre>" y imagen "<imagen>" y raza "<raza>" y estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la imagen "<imagen>" y la raza "<raza>" y el estado "<estado>"

    Examples:
      | nombre | imagen | raza | estado |
      | luna   | https://scontent.ftru8-1.fna.fbcdn.net/v/t39.30808-6/474694769_3470192903124986_111707657248604342_n.jpg?stp=dst-jpg_tt6&cstp=mx720x720&ctp=s720x720&_nc_cat=100&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeGbG5-hj57i7QePFgGD9rJ8RfEUIFFxw6RF8RQgUXHDpKnLHQYG5VpB-xMJiBE-imtrlLarx5gbG34F4xIHzvV7&_nc_ohc=LA1uBX-ek8IQ7kNvwEVUQzO&_nc_oc=AdqKrncAfJ3NT1sj8VdtLsRw_KTNBJi5Y9Fw7e_u483MbvRGAjEpxDHveLWpv9rPrxU&_nc_zt=23&_nc_ht=scontent.ftru8-1.fna&_nc_gid=pNLO-L7mu619T99pQI9SEg&_nc_ss=7b2a8&oh=00_AQDyzlyMiWHq2bF58-hK759Vh0ioAbcbgnE_WbZIY2TOhA&oe=6A5CC36A | labrador | disponible |
      | max    | https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN6XDDzv9M4GS5M2cPQDJtsE5po13yTk_4p-jjuwemYQ&s=10 | poodle | pendiente |
      | lhyo   | https://i.pinimg.com/474x/f9/db/01/f9db01efbae3cc0e219156ddda3f7a8b.jpg | beagle | adoptado |

  Scenario Outline: Consultar una mascota por ID
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y imagen "<imagen>" y raza "<raza>" y estado "<estado>"
    When el actor consulta la mascota por su ID
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la imagen "<imagen>" y la raza "<raza>" y el estado "<estado>"

    Examples:
      | nombre | imagen | raza | estado |
      | luna   | https://scontent.ftru8-1.fna.fbcdn.net/v/t39.30808-6/474694769_3470192903124986_111707657248604342_n.jpg?stp=dst-jpg_tt6&cstp=mx720x720&ctp=s720x720&_nc_cat=100&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeGbG5-hj57i7QePFgGD9rJ8RfEUIFFxw6RF8RQgUXHDpKnLHQYG5VpB-xMJiBE-imtrlLarx5gbG34F4xIHzvV7&_nc_ohc=LA1uBX-ek8IQ7kNvwEVUQzO&_nc_oc=AdqKrncAfJ3NT1sj8VdtLsRw_KTNBJi5Y9Fw7e_u483MbvRGAjEpxDHveLWpv9rPrxU&_nc_zt=23&_nc_ht=scontent.ftru8-1.fna&_nc_gid=pNLO-L7mu619T99pQI9SEg&_nc_ss=7b2a8&oh=00_AQDyzlyMiWHq2bF58-hK759Vh0ioAbcbgnE_WbZIY2TOhA&oe=6A5CC36A | labrador | disponible |
      | max    | https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN6XDDzv9M4GS5M2cPQDJtsE5po13yTk_4p-jjuwemYQ&s=10 | poodle | pendiente |
      | lhyo   | https://i.pinimg.com/474x/f9/db/01/f9db01efbae3cc0e219156ddda3f7a8b.jpg | beagle | adoptado |

  Scenario Outline: Actualizar el estado de una mascota
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y imagen "<imagen>" y raza "<raza>" y estado "<estado_inicial>"
    When el actor actualiza el estado de la mascota a "<estado_final>" con el nombre "<nombre>" y imagen "<imagen>" y raza "<raza>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la imagen "<imagen>" y la raza "<raza>" y el estado "<estado_final>"

    Examples:
      | nombre | imagen | raza | estado_inicial | estado_final |
      | luna   | https://scontent.ftru8-1.fna.fbcdn.net/v/t39.30808-6/474694769_3470192903124986_111707657248604342_n.jpg?stp=dst-jpg_tt6&cstp=mx720x720&ctp=s720x720&_nc_cat=100&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeGbG5-hj57i7QePFgGD9rJ8RfEUIFFxw6RF8RQgUXHDpKnLHQYG5VpB-xMJiBE-imtrlLarx5gbG34F4xIHzvV7&_nc_ohc=LA1uBX-ek8IQ7kNvwEVUQzO&_nc_oc=AdqKrncAfJ3NT1sj8VdtLsRw_KTNBJi5Y9Fw7e_u483MbvRGAjEpxDHveLWpv9rPrxU&_nc_zt=23&_nc_ht=scontent.ftru8-1.fna&_nc_gid=pNLO-L7mu619T99pQI9SEg&_nc_ss=7b2a8&oh=00_AQDyzlyMiWHq2bF58-hK759Vh0ioAbcbgnE_WbZIY2TOhA&oe=6A5CC36A | labrador | disponible | adoptado |
      | max    | https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN6XDDzv9M4GS5M2cPQDJtsE5po13yTk_4p-jjuwemYQ&s=10 | poodle | pendiente | disponible |
      | lhyo   | https://i.pinimg.com/474x/f9/db/01/f9db01efbae3cc0e219156ddda3f7a8b.jpg | beagle | adoptado | disponible |

  Scenario Outline: Eliminar una mascota y validar su inexistencia
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y imagen "<imagen>" y raza "<raza>" y estado "<estado>"
    When el actor elimina la mascota
    Then el codigo de respuesta debe ser 200
    And al intentar consultar nuevamente su ID el codigo de respuesta debe ser 404

    Examples:
      | nombre | imagen | raza | estado |
      | luna   | https://scontent.ftru8-1.fna.fbcdn.net/v/t39.30808-6/474694769_3470192903124986_111707657248604342_n.jpg?stp=dst-jpg_tt6&cstp=mx720x720&ctp=s720x720&_nc_cat=100&ccb=1-7&_nc_sid=127cfc&_nc_eui2=AeGbG5-hj57i7QePFgGD9rJ8RfEUIFFxw6RF8RQgUXHDpKnLHQYG5VpB-xMJiBE-imtrlLarx5gbG34F4xIHzvV7&_nc_ohc=LA1uBX-ek8IQ7kNvwEVUQzO&_nc_oc=AdqKrncAfJ3NT1sj8VdtLsRw_KTNBJi5Y9Fw7e_u483MbvRGAjEpxDHveLWpv9rPrxU&_nc_zt=23&_nc_ht=scontent.ftru8-1.fna&_nc_gid=pNLO-L7mu619T99pQI9SEg&_nc_ss=7b2a8&oh=00_AQDyzlyMiWHq2bF58-hK759Vh0ioAbcbgnE_WbZIY2TOhA&oe=6A5CC36A | labrador | disponible |
      | max    | https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTN6XDDzv9M4GS5M2cPQDJtsE5po13yTk_4p-jjuwemYQ&s=10 | poodle | pendiente |
      | lhyo   | https://i.pinimg.com/474x/f9/db/01/f9db01efbae3cc0e219156ddda3f7a8b.jpg | beagle | adoptado |
