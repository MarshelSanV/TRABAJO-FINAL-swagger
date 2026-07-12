@PetStore
Feature: Gestión de Mascotas en Swagger PetStore

  Scenario Outline: Crear una mascota exitosamente
    Given que el actor establece el endpoint de PetStore
    When el actor registra una mascota con nombre "<nombre>" y estado "<estado>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y el estado "<estado>"

    Examples:
      | nombre | estado     |
      | luna   | disponible |
      | max    | pendiente  |
      | lhyo   | vendido    |

  Scenario Outline: Consultar una mascota por ID
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y estado "<estado>"
    When el actor consulta la mascota por su ID
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y el estado "<estado>"

    Examples:
      | nombre | estado     |
      | luna   | disponible |
      | max    | pendiente  |
      | lhyo   | vendido    |

  Scenario Outline: Actualizar el estado de una mascota
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y estado "<estado_inicial>"
    When el actor actualiza el estado de la mascota a "<estado_final>" con el nombre "<nombre>"
    Then el codigo de respuesta debe ser 200
    And la respuesta debe contener el nombre "<nombre>" y el estado "<estado_final>"

    Examples:
      | nombre | estado_inicial | estado_final |
      | luna   | disponible     | vendido      |
      | max    | pendiente      | disponible   |
      | lhyo   | vendido        | disponible   |

  Scenario Outline: Eliminar una mascota y validar su inexistencia
    Given que el actor establece el endpoint de PetStore
    And existe una mascota registrada con nombre "<nombre>" y estado "<estado>"
    When el actor elimina la mascota
    Then el codigo de respuesta debe ser 200
    When el actor consulta la mascota por su ID
    Then el codigo de respuesta debe ser 404

    Examples:
      | nombre | estado     |
      | luna   | disponible |
      | max    | pendiente  |
      | lhyo   | vendido    |
