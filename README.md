GDCalaga
========

The first game that I started with GDC.

The project is setup for compilation and execution within Eclipse. You may download it here:

http://www.eclipse.org/downloads/

The Eclipse Classic package is recommend, but the JDT package would be sufficient. All GDCalaga dependencies have been added to the repository so just doing a checkout should get you everything you need.

To build and run GDCalaga:
1. Install Eclipse
2. Checkout/clone the repo
3. Import the project into Eclipse (Right Click on Package Explorer->Import->Existing Project Into Workspace)
4. Follow the dialogs as needed (make sure copy the project into your workspace is unchecked)
5. Once project builds, right click on project and then choose Run As->Java Application

GDCalaga should launch and be good to go. Running outside of Eclipse is currently disabled due to classpath resolution issues that won't be an issue until release time. If another IDE setup is desired, feel free to configure a project file for it and check it into the repo, just be mindful of the current setup and make sure you don't break it.

Enjoy!
