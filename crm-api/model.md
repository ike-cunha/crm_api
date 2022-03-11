https://github.com/mermaidjs/mermaidjs.github.io/blob/master/classDiagram.md

```mermaid
classDiagram

class Customer{
    +String firstName
    +String lastName
}

class User{
    +String userName
    +Boolean isAdmin
}

Animal -- Duck

```

```
<\|--| Inheritance
*--  | Composition
o--  | Aggregation
-->  | Association
--   | Link 
```