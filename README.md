# quarkus-example

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-example-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and Jakarta Persistence
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, Jakarta Persistence)
- RESTEasy Classic JSON-B ([guide](https://quarkus.io/guides/rest-json)): JSON-B serialization support for RESTEasy Classic
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache)): Simplify your persistence code for Hibernate ORM via the active record or the repository pattern
- JDBC Driver - MySQL ([guide](https://quarkus.io/guides/datasource)): Connect to the MySQL database via JDBC

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

[Related Hibernate with Panache section...](https://quarkus.io/guides/hibernate-orm-panache)


### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)


const data = "YOUR_3MB_BASE64_STRING_HERE"; 
const chunkSize = 1500; // Large text blocks per grid cell
const numCells = 16;    // 4x4 grid = 16 blocks per screen
const frameDuration = 1500; // 1.5 seconds per screen change

// 1. Setup fullscreen black layout
document.body.innerHTML = '';
document.body.style.cssText = 'background:black; color:white; font-family:monospace; font-size:12px; margin:0; padding:10px; box-sizing:border-box; display:grid; grid-template-columns:repeat(4,1fr); grid-template-rows:repeat(4,1fr); height:100vh; width:100vw; gap:10px; overflow:hidden;';

let index = 0;
let frameCount = 0;

function showNextFrame() {
  if (index >= data.length) {
    document.body.innerHTML = '<div style="grid-column:span 4; font-size:40px; color:green; text-align:center; margin-top:20vh;">TRANSFER COMPLETE</div>';
    return;
  }

  document.body.innerHTML = '';
  frameCount++;

  // Fill the 16 grid cells
  for (let c = 0; c < numCells; c++) {
    if (index < data.length) {
      const cell = document.createElement('div');
      cell.style.cssText = 'border:1px solid #333; padding:5px; word-break:break-all; overflow:hidden; font-size:10px; line-height:1.1;';
      
      // Prefix with [Frame_Cell] index so you can reassemble it perfectly later
      const chunk = data.substring(index, index + chunkSize);
      cell.innerText = `[F${String(frameCount).padStart(3,'0')}_C${String(c).padStart(2,'0')}]${chunk}`;
      
      document.body.appendChild(cell);
      index += chunkSize;
    }
  }
  
  setTimeout(showNextFrame, frameDuration);
}

showNextFrame();
