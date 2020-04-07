[![MIT License][license-badge]][LICENSE]

# Truecaller Touring Test

> Home assignment created by [Peter Hugosson-Miller][oldmanlink-profile] as part of the recruitment process for Truecaller. Make a service to calculate and display tours of a game piece according to given rules 
> 
> Implemented in Play/Scala on the backend and ReactJS on the frontend. Based on the excellent [Scala Play React Seed](http://bit.ly/2A1AzEq) .
 
![RunningTour](https://github.com/OldManLink/truetest/blob/master/docs/05_RunningTour.gif)

## How to build it?

### Prerequisites

* [Java 8](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
* [Node.js](https://nodejs.org/)
* [Yarn](https://classic.yarnpkg.com/en/docs/install/)
* [sbt](https://www.scala-sbt.org/download.html)
* [scala](https://www.scala-lang.org/download/)

### Let's get started,

* Fork or clone this repository.

* Use any of the following [SBT](http://www.scala-sbt.org/) commands which will intern trigger frontend associated npm scripts.

```
    sbt clean           # Clean existing build artifacts

    sbt stage           # Build your application from your project’s source directory

    sbt run             # Run both backend and frontend builds in watch mode

    sbt dist            # Build both backend and frontend sources into a single distribution artifact

    sbt test            # Run both backend and frontend unit tests
```

* This seed is not using [scala play views](https://www.playframework.com/documentation/2.6.x/ScalaTemplates). All the views and frontend associated routes are served via [React](https://reactjs.org/) code base under `ui` directory.

## Complete Directory Layout

```
├── /app/                                 # The backend (scala play) sources (controllers, models, services)
│     └── /controllers/                   # Backend controllers
│           └── FrontendController.scala  # Asset controller wrapper serving frontend assets and artifacts
├── /conf/                                # Configurations files and other non-compiled resources (on classpath)
│     ├── application.conf                # Play application configuratiion file.
│     ├── logback.xml                     # Logging configuration
│     └── routes                          # Routes definition file
├── /docs/                                # Documents and screenshots
├── /logs/                                # Log directory
│     └── application.log                 # Application log file
├── /project/                             # Contains project build configuration and plugins
│     ├── FrontendCommands.scala          # Frontend build command mapping configuration
│     ├── FrontendRunHook.scala           # Forntend build PlayRunHook (trigger frontend serve on sbt run)
│     ├── build.properties                # Marker for sbt project
│     └── plugins.sbt                     # SBT plugins declaration
├── /public/                              # Frontend build artifacts will be copied to this directory
├── /target/                              # Play project build artifact directory
│     ├── /universal/                     # Application packaging
│     └── /web/                           # Compiled web assets
├── /test/                                # Contains unit tests of backend sources
├── /ui/                                  # React frontend source (based on Create React App)
│     ├── /public/                        # Contains the index.html file
│     ├── /node_modules/                  # 3rd-party frontend libraries and utilities
│     ├── /src/                           # The frontend source codebase of the application
│     ├── .editorconfig                   # Define and maintain consistent coding styles between different editors and IDEs
│     ├── .gitignore                      # Contains ui files to be ignored when pushing to git
│     ├── package.json                    # NPM configuration of frontend source
│     ├── README.md                       # Contains all user guide details for the ui
│     └── yarn.lock                       # Yarn lock file
├── .gitignore                            # Contains files to be ignored when pushing to git
├── build.sbt                             # Play application SBT configuration
├── LICENSE                               # License Agreement file
├── README.md                             # Application user guide
└── ui-build.sbt                          # SBT command hooks associated with frontend npm scripts 
```

**Note: _On production build all the front end React build artifacts will be copied to the `public` folder._**

## How to use it?

### Start the server and frontend
Open a console window and enter this command:
```
sbt run
```
This will open your browser on the address `http://localhost:3000/` with the following view:

![EmptyBoard](https://github.com/OldManLink/truetest/blob/master/docs/00_EmptyBoard.png)

The board dimensions can be altered by editing `ui/src/App.js` line 12, see the `rows` and `cols` parameters. Currently supported dimensions are 5x5 and 10x10.

Click on any board square to fetch a list of possible tours from that square. The list will look like this:

![ToursList](https://github.com/OldManLink/truetest/blob/master/docs/01_ToursList.png)

The number of Tours fetched is also configured in the same place as the board dimensions, see the `maxTours` parameter.

Click on any Tour to se it playback on the board. As each square is visited it tuns green. 

![PlayingTour](https://github.com/OldManLink/truetest/blob/master/docs/02_PlayingTour.png)

The system is quite robust, since it is written in Scala, but with my initial solution any board sizes bigger that 5x5 caused my machine to crash with an [out of memory error](https://github.com/OldManLink/truetest/blob/master/docs/stackTrace.txt). Instead of spending too much time investigating that, I decided to switch to a Divide and Conquer strategy that chops the board up into quarters, solves them, and puts the solutions back together again. This is how the resulting tour can look like: ![TourDone](https://github.com/OldManLink/truetest/blob/master/docs/03_TourDone.png)

### Testing and Test Coverage
To run the test suite, enter this command:
```
sbt test
```

There is comprehensive test coverage measurement provided by SCoverage. After a successful test suite run, it can be generated with this command:
```
sbt coverage test coverageReport
```
The test coverage results will be accessible from target/scala-2.12/scoverage-report/index.html
![Coverage](https://github.com/OldManLink/truetest/blob/master/docs/04_Coverage.png)


### Stop the server and frontend
To stop the client smoothly, simply reload the page on your browser. React will smoothly unmount and terminate any running tours. Once this is done, press `<Ctrl> C` in the console to terminate the backend and frontend services.

## Further discussion points
I'm tempted to put all my thoughts here in the documentation, but then I would never get this sent off, so I'll have to stop here, then look at the aforementioned memory issue. If you want to know more, please call me back for a second interview!


## License

This software is licensed under the MIT license

[license-badge]: http://img.shields.io/badge/license-MIT-blue.svg?style=flat
[license]: https://github.com/yohangz/java-play-react-seed/blob/master/README.md

[oldmanlink-profile]: https://github.com/oldmanlink
