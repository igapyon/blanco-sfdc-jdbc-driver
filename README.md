# Blanco SFDC JDBC Driver

Blanco SFDC JDBC Driver is a JDBC Driver to access SFDC using SOQL.

- This Driver is designed for Programmer. This Driver is simple and lowlevel wrapper of Partner WSDL.
- Implemented as OSS and written in Java.
- Only for SOQL Query, without insert/update/delete feature.

## Status

- Under construction.

## Note

- No insert/update/delete support.
- No DWH support.
- No support.
- Read source code yourself.

## How to build

```sh
git clone https://github.com/igapyon/blanco-sfdc-jdbc-driver.git
cd blanco-sfdc-jdbc-driver/
mvn package
ls ./target/blanco-sfdc-jdbc-driver-1.0-SNAPSHOT.jar 
ls ./target/dependency/
```

## How to run with squirrelsql

[squirrelsql](http://squirrel-sql.sourceforge.net/) により大雑把に動作します。

ちなみに squirrelsql を乱暴に上書きして動作させる場合の手順は以下です。もっと正しい手順については各自検討してください。

```sh
cp -p ./target/blanco-sfdc-jdbc-driver-1.0-SNAPSHOT.jar /tmp/squirrelsql-3.8.0-standard/lib/
cp -p ./target/dependency/* /tmp/squirrelsql-3.8.0-standard/lib/
```

### How to connect from squirrelsql to SFDC

```
Name: Salesforce SOQL RO
Example URL: blanco:sfdc:jdbc:https://login.salesforce.com/services/Soap/u/40.0
Website URL: https://github.com/igapyon/blanco-sfdc-jdbc-driver
Class Name: blanco.sfdc.jdbc.driver.BlancoSfdcJdbcDriver
```

# LICENSE

This software is licensed under the Apache License or the GNU Lesser General Public License or both.

```
/*
 *  blanco-jdbc-driver-simple
 *  Copyright (C) 2017  Toshiki Iga
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
```

```
/*
 *  Copyright 2017 Toshiki Iga
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
```

