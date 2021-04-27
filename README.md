To run a specific file:
```
mvn gatling:test -Dgatling.simulationClass=simulations.{file_name}
```

or 

for custom params on the "runTimeParams.scala" file:

```
mvn gatling:test -Dgatling.simulationClass=simulations.runtimeParams -DUSERS=10 -DRAMP_DURATION=5 -DDURATION=30
```