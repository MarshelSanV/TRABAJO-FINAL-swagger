@PetStore
Feature: Gestión de Mascotas en Swagger PetStore


  Scenario Outline: Crear una mascota exitosamente
    Given que el actor establece el endpoint de PetStore
    When el actor registra una mascota con nombre "<nombre>" y raza "<raza>" y estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la raza "<raza>" y el estado "<estado>"

    Examples:
      | nombre | raza      | estado     |
      | luna   | labrador  | disponible |
      | max    | poodle    | pendiente  |
      | lhyo   | beagle    | adoptado   |

  Scenario Outline: Consultar una mascota por ID
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y raza "<raza>" y estado "<estado>"
    When el actor consulta la mascota por su ID
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la raza "<raza>" y el estado "<estado>"

    Examples:
      | nombre | raza      | estado     |
      | luna   | labrador  | disponible |
      | max    | poodle    | pendiente  |
      | lhyo   | beagle    | adoptado   |

  Scenario Outline: Actualizar el estado de una mascota
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y raza "<raza>" y estado "<estado_inicial>"
    When el actor actualiza el estado de la mascota a "<estado_final>" con el nombre "<nombre>" y raza "<raza>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y la raza "<raza>" y el estado "<estado_final>"

    Examples:
      | nombre | raza      | estado_inicial | estado_final |
      | luna   | labrador  | disponible     | adoptado     |
      | max    | poodle    | pendiente      | disponible   |
      | lhyo   | beagle    | adoptado       | disponible   |

  Scenario Outline: Eliminar una mascota y validar su inexistencia
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y raza "<raza>" y estado "<estado>"
    When el actor elimina la mascota
    Then el codigo de respuesta debe ser 200
    And al intentar consultar nuevamente su ID el codigo de respuesta debe ser 404

    Examples:
      | nombre | raza      | estado     |
      | luna   | labrador  | disponible |
      | max    | poodle    | pendiente  |
      | lhyo   | beagle    | adoptado   |
