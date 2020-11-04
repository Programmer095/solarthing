# Updating
To update, run these commands:
```shell script
git pull
program/download_solarthing.sh # if there's an update, this will download it
```
If you are running the systemd service, do this to restart:
```shell script
sudo systemctl restart solarthing-<program type>
```

## I don't have permission
If you don't have permission to update, it is recommended you run:
```shell script
sudo other/linux/create_user.sh
sudo other/linux/update_perms.sh
sudo usermod -a -G solarthing,dialout,tty,video,gpio $USER
# Or use this if you don't need the gpio (if your system doesn't have the gpio group)
sudo usermod -a -G solarthing,dialout,tty,video $USER
```
This should have already been run if you installed SolarThing using the one line command.
