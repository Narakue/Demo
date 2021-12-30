# project  demo

# 数据库建表语句：

``` SQL
CREATE TABLE `tbl_teacher` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `qq` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `selected` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8

CREATE TABLE `tbl_student` (
  `id` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `qq` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8

CREATE TABLE `tbl_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `basic_requirement` text,
  `research_objective` text,
  `reference` text,
  `amount` int(11) DEFAULT NULL,
  `selected` int(11) DEFAULT NULL,
  `passed` int(11) DEFAULT NULL,
  `teacher_id` varchar(20) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tbl_subject_tbl_teacher_id_fk` (`teacher_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE `tbl_choose_subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_id` int(11) DEFAULT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  `choose_time` datetime DEFAULT NULL,
  `confirm_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tbl_choose_subject_tbl_student_id_fk` (`student_id`),
  KEY `tbl_choose_subject_tbl_subject_id_fk` (`subject_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8

CREATE TABLE `tbl_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(255) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8
```