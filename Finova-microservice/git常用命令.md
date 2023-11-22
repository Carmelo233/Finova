# Git常用命令

![preview](pics/git常用命令/v2-3bc9d5f2c49a713c776e69676d7d56c5_r.jpg)

## 初始克隆

```
git clone 从git或gitlab上克隆的地址
```

在运行下列命令时，需要`cd`进仓库的根目录，即带有`.git`文件夹的目录

## 本地添加文件

```
# 查看当前暂存区和本地仓库的状态,此时可以看到被修改的文件，新增的文件等信息，此步可省略
git status

# 添加到暂存区（index），暂存区位于.git文件夹内
git add <filePath>
# 添加所有变化，包括增删改
git add -A 
# 添加增改的文件，不删除本地已删除的文件
git add .

# 查看当前暂存区和本地仓库的状态,此时可以看到位于暂存区的文件，此步可省略
git status

# 提交到本地仓库（Repository），本地仓库位于.git文件夹内
git commit -m "备注"

# 此时查看状态会提示你已经提交了文件，可以使用push命令推送到远程目录，此步可省略
git status

# 推送给远程仓库（Remote），由于只有一个master分支，默认为master分支
git push
```

## 从远程仓库更新本地仓库

```
git pull
```

## 添加gitignore中的忽略项后不起作用的方法

此方法运行后，gitignore中的配置的忽略项所指文件或文件夹会在远程仓库中被删除，即远程仓库中不会存在gitignore中配置的忽略项所指文件或文件夹

```
# 1. 清除本地当前的Git缓存
git rm -r --cached .

# 2. 应用.gitignore等本地配置文件重新建立Git索引
git add .

# 3. （可选）提交当前Git版本并备注说明
git commit -m 'update .gitignore'
```

## 在本仓库添加其它仓库的引用，即添加子模块

```
cd 到本仓库要添加外部仓库的指定目录

# 添加外部仓库b-project，添加子模块b-project
git submodule add https://gitlab.com/b-project

# 提交子模块
git add.
git commit -m "add submodule"
git push

# 提交后在git网页上可以点击该文件夹跳转到其它仓库
```

解决pull后，子模块没有同步最新内容的问题

```
git submodule init
git submodule sync
git submodule update
```

## 解决git冲突

出现这个问题的原因是其他人修改了xxx.php并提交到版本库中去了，而你本地也修改了xxx.php，

```
git stash
git pull
git stash pop
```

通过git stash将工作区恢复到上次提交的内容，同时备份本地所做的修改，之后就可以正常git pull了，git pull完成后，执行git stash pop将之前本地做的修改应用到当前工作区。

git stash: 备份当前的工作区的内容，从最近的一次提交中读取相关内容，让工作区保证和上次提交的内容一致。同时，将当前的工作区内容保存到Git栈中。

git stash pop: 从Git栈中读取最近一次保存的内容，恢复工作区的相关内容。由于可能存在多个Stash的内容，所以用栈来管理，pop会从最近的一个stash中读取内容并恢复。

git stash list: 显示Git栈内的所有备份，可以利用这个列表来决定从那个地方恢复。

git stash clear: 清空Git栈。。

## 拉最新版本的仓库，覆盖本地的修改

```
# 从远程下载最新的，而不尝试合并或rebase任何东西。
git fetch --all

# 然后git reset将主分支重置为您刚刚获取的内容。 
# --hard 参数撤销工作区中所有未提交的修改内容，将暂存区与工作区都回到上一次版本，并删除之前的所有信息提交：
git reset --hard origin/master
```

## 撤销add操作

```
git reset HEAD 如果后面什么都不跟的话 就是上一次add 里面的全部撤销了
git reset HEAD XXX/XXX/XXX 就是对某个文件进行撤销了
```

