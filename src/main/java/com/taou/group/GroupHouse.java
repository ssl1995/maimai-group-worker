package com.taou.group;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author songshenglin
 * @date 2021/9/22 23:36
 * @description
 */
public class GroupHouse {
    private static List<List<String>> inits;

    private static List<Worker> allWorkers = Lists.newArrayList();

//    private static List<Worker> allMan = Lists.newArrayList();
//    private static List<Worker> allWomen = Lists.newArrayList();

    private static final int CUR_YEAR = 2021;


    /**
     * 分房：员工不超过5岁，新员工带老员工
     * 确认：2个人一间房？ 几个人一间房？
     *
     * @param args
     */
    public static void main(String[] args) {
        // 将原始数据转换为allWorkers
        inits = initWorkers();
        for (List<String> list : inits) {
            Worker worker = new Worker();
            worker.setUserName(list.get(0));
            worker.setDepartment(list.get(1));
            worker.setSex("男".equals(list.get(2)) ? 0 : 1);
            worker.setBirthTime(Integer.parseInt(list.get(3).substring(0, list.get(3).indexOf("/"))));
            worker.setHireTime((CUR_YEAR - Integer.parseInt(list.get(4).substring(0, list.get(4).indexOf("/")))) > 1);
            worker.setLevel(list.get(5));
            allWorkers.add(worker);
        }

        System.out.println("allWorkers:" + allWorkers.size());// 538
        List<Worker> allMan = allWorkers.stream().filter(worker -> worker.getSex() == 0).collect(Collectors.toList());
        List<Worker> allWomen = allWorkers.stream().filter(worker -> worker.getSex() == 1).collect(Collectors.toList());
        System.out.println("allMan:" + allMan.size());// 290
        System.out.println("allWomen:" + allWomen.size());// 248
        System.out.println("----开始分房----");

        List<List<Worker>> manRes = Lists.newArrayList();
        int count1 = allMan.size() / 2;
        for (int i = 0; i < count1; i++) {
            List<Worker> oneGroup = getOneGroup(allMan);
            manRes.add(oneGroup);
        }
//        System.out.println("manRes:" + manRes.size());// 145
//        System.out.println("------------------manRes----------------");
//        for (List<Worker> workers : manRes) {
//            workers.forEach(worker -> {
//                String old = worker.getHireTime() ? "老" : "新";
//                System.out.println(worker.getUserName() + "\t" + worker.getDepartment() + "\t" + worker.getSex() + "\t" + worker.getBirthTime() + "\t" + old + "\t" + worker.getLevel());
//            });
//            System.out.println("----------------------------------");
//        }


        List<List<Worker>> womanRes = Lists.newArrayList();
        int count2 = allWomen.size() / 2;
        for (int i = 0; i < count2; i++) {
            List<Worker> oneGroup = getOneGroup(allWomen);
            womanRes.add(oneGroup);
        }
        System.out.println("womanRes:" + womanRes.size());// 124
        System.out.println("------------------womanRes----------------");
        for (List<Worker> workers : womanRes) {
            workers.forEach(worker -> {
                String old = worker.getHireTime() ? "老" : "新";
                System.out.println(worker.getUserName() + "\t" + worker.getDepartment() + "\t" + worker.getSex() + "\t" + worker.getBirthTime() + "\t" + old + "\t" + worker.getLevel());
            });
            System.out.println("----------------------------------");
        }
    }

    public static List<Worker> getOneGroup(List<Worker> workers) {
        List<Worker> oneGroup = new ArrayList<>();

        Worker worker = workers.get((int) (Math.random() * workers.size()));
        oneGroup.add(worker);
        workers.remove(worker);
        String depart = worker.getDepartment();
        Boolean isOld = worker.getHireTime();

        Worker next = getNextOne(workers, depart, isOld);
        if (!Objects.isNull(next)) {
            workers.remove(next);
        } else {
            next = workers.get((int) (Math.random() * workers.size()));
            workers.remove(next);
        }
        oneGroup.add(next);
        return oneGroup;
    }

    public static Worker getNextOne(List<Worker> workers, String depart, Boolean isOld) {
        List<Worker> nextList = workers.stream()
                .filter(w -> !Objects.equals(w.getHireTime(), isOld))
                .filter(w -> !Objects.equals(w.getDepartment(), depart))
                .collect(Collectors.toList());
        if (nextList.size() == 0) {
            return null;
        }
        return nextList.get((int) (Math.random() * nextList.size()));
    }


    // 获得原始的String数据
    public static List<List<String>> initWorkers() {
        List<List<String>> workers = Lists.newArrayList();
        workers.add(Lists.newArrayList("林凡", "CEO", "男", "1980/7/2", "2012/1/20", "是"));
        workers.add(Lists.newArrayList("张晓萌", "P&N技术", "男", "1989/12/3", "2018/9/17", ""));
        workers.add(Lists.newArrayList("于晓旭", "P&N技术", "男", "1991/1/11", "2019/9/18", ""));
        workers.add(Lists.newArrayList("赵璐", "P&N技术", "女", "1993/10/18", "2018/1/2", ""));
        workers.add(Lists.newArrayList("郭丹丹", "P&N技术", "女", "1997/2/13", "2021/3/22", ""));
        workers.add(Lists.newArrayList("孙礼媛", "P&N运营", "女", "1989/2/24", "2019/10/14", ""));
        workers.add(Lists.newArrayList("许梦卓", "P&N产品", "女", "1989/8/25", "2021/8/23", ""));
        workers.add(Lists.newArrayList("徐欢", "P&N运营", "男", "1997/9/7", "2021/3/10", ""));
        workers.add(Lists.newArrayList("周文", "P&N技术", "男", "1992/8/29", "2020/7/13", ""));
        workers.add(Lists.newArrayList("徐云茜", "P&N事业部", "女", "1988/8/13", "2017/12/26", "是"));
        workers.add(Lists.newArrayList("李鸿慧", "P&N技术", "女", "1997/11/24", "2021/3/29", ""));
        workers.add(Lists.newArrayList("翟猛", "P&N技术", "男", "1990/2/12", "2020/5/20", ""));
        workers.add(Lists.newArrayList("邱陆静", "P&N运营", "女", "1993/6/26", "2020/10/12", ""));
        workers.add(Lists.newArrayList("刘爽", "P&N技术", "女", "1995/1/28", "2021/3/10", ""));
        workers.add(Lists.newArrayList("李航", "P&N技术", "男", "1998/5/22", "2021/7/7", ""));
        workers.add(Lists.newArrayList("张伟一", "P&N产品", "男", "1990/12/22", "2021/8/23", ""));
        workers.add(Lists.newArrayList("董诺", "商业运营", "男", "1988/5/21", "2020/3/25", ""));
        workers.add(Lists.newArrayList("唐乐嘉", "渠道销售", "女", "1986/8/12", "2019/8/19", ""));
        workers.add(Lists.newArrayList("李蓓瑶", "策略市场", "女", "1990/9/21", "2021/3/10", ""));
        workers.add(Lists.newArrayList("毛翰彬", "内容运营", "男", "1990/12/15", "2020/8/17", ""));
        workers.add(Lists.newArrayList("何金", "广告事业部", "男", "1981/6/5", "2015/8/3", "是"));
        workers.add(Lists.newArrayList("姚鑫", "华东区域", "女", "1983/9/15", "2019/11/1", ""));
        workers.add(Lists.newArrayList("占煌", "产品研发", "男", "1997/11/9", "2021/6/7", ""));
        workers.add(Lists.newArrayList("宋树敏", "营销策划", "女", "1993/7/24", "2021/5/19", ""));
        workers.add(Lists.newArrayList("杜威", "华南销售", "男", "1989/5/15", "2017/12/26", ""));
        workers.add(Lists.newArrayList("杨小林", "渠道销售", "男", "1985/8/29", "2020/6/8", ""));
        workers.add(Lists.newArrayList("刘紫煜", "营销策划", "女", "1991/12/5", "2019/1/7", ""));
        workers.add(Lists.newArrayList("高欣欣", "华北销售", "女", "1994/6/19", "2021/5/19", ""));
        workers.add(Lists.newArrayList("刘潇丝", "商业运营", "女", "1984/4/16", "2020/6/1", ""));
        workers.add(Lists.newArrayList("冯嘉", "华北销售", "男", "1982/4/28", "2021/5/17", ""));
        workers.add(Lists.newArrayList("薛厚", "渠道销售", "男", "1989/9/1", "2020/5/6", ""));
        workers.add(Lists.newArrayList("李倩", "商业运营", "女", "1988/7/4", "2019/1/23", ""));
        workers.add(Lists.newArrayList("陈亚戈", "渠道销售", "男", "1987/7/11", "2020/3/4", ""));
        workers.add(Lists.newArrayList("焦亚男", "内容运营", "女", "1996/1/8", "2021/8/4", ""));
        workers.add(Lists.newArrayList("孙超", "渠道销售", "男", "1988/9/30", "2020/11/23", ""));
        workers.add(Lists.newArrayList("刘旭", "商业运营", "男", "1999/11/26", "2021/7/1", ""));
        workers.add(Lists.newArrayList("姚康宝", "产品研发", "男", "1986/5/30", "2017/7/4", ""));
        workers.add(Lists.newArrayList("田少健", "产品研发", "男", "1990/4/1", "2020/10/14", ""));
        workers.add(Lists.newArrayList("张进伟", "产品研发", "男", "1990/9/17", "2018/6/25", ""));
        workers.add(Lists.newArrayList("姚欣如", "华北销售", "女", "1985/6/18", "2021/3/8", ""));
        workers.add(Lists.newArrayList("夏侯彩平", "华东销售", "女", "1984/1/1", "2019/11/13", ""));
        workers.add(Lists.newArrayList("徐俊", "广告事业部", "男", "1982/1/6", "2012/4/12", ""));
        workers.add(Lists.newArrayList("刘贞儒", "产品研发", "女", "1995/3/23", "2021/9/1", ""));
        workers.add(Lists.newArrayList("姜鹤", "策略市场", "男", "1989/10/5", "2019/7/24", ""));
        workers.add(Lists.newArrayList("张明明", "广告事业部", "女", "1981/10/6", "2020/2/17", "是"));
        workers.add(Lists.newArrayList("刘璐", "华北区域", "女", "1993/8/1", "2021/7/14", ""));
        workers.add(Lists.newArrayList("常文浩", "商业运营", "男", "1992/11/23", "2021/4/19", ""));
        workers.add(Lists.newArrayList("许琴琴", "电商KA", "女", "1984/12/18", "2020/4/8", ""));
        workers.add(Lists.newArrayList("彭亭亭", "电商KA", "女", "1987/5/1", "2021/8/30", ""));
        workers.add(Lists.newArrayList("唐柳清", "策略市场", "女", "1988/8/28", "2019/2/26", ""));
        workers.add(Lists.newArrayList("李最孜", "营销策划", "女", "1995/6/9", "2021/1/13", ""));
        workers.add(Lists.newArrayList("唐彦斌", "华东销售", "男", "1990/1/10", "2020/8/17", ""));
        workers.add(Lists.newArrayList("李文军", "产品研发", "男", "1989/7/14", "2021/5/10", ""));
        workers.add(Lists.newArrayList("赵云", "电商KA", "女", "1989/10/24", "2020/3/24", ""));
        workers.add(Lists.newArrayList("姬慧敏", "营销策划", "女", "1990/12/25", "2021/6/21", ""));
        workers.add(Lists.newArrayList("孟云", "内容运营", "女", "1988/9/30", "2019/5/15", ""));
        workers.add(Lists.newArrayList("郑嵩", "华南区域", "男", "1988/4/7", "2015/1/26", ""));
        workers.add(Lists.newArrayList("王磊", "专家网络", "男", "1989/6/3", "2021/8/16", ""));
        workers.add(Lists.newArrayList("Sindy", "销售部门", "女", "1991/8/17", "2021/5/24", ""));
        workers.add(Lists.newArrayList("付新辉", "会员产品", "男", "1996/8/11", "2021/6/16", ""));
        workers.add(Lists.newArrayList("陈玥", "会员事业部", "女", "1986/3/6", "2016/8/19", "是"));
        workers.add(Lists.newArrayList("李鹏龙", "咨询部门", "男", "1993/6/16", "2021/4/12", ""));
        workers.add(Lists.newArrayList("张泊宇", "会员产品", "男", "1992/4/6", "2020/9/9", ""));
        workers.add(Lists.newArrayList("贾亚芳", "会员技术", "女", "1994/2/22", "2021/9/1", ""));
        workers.add(Lists.newArrayList("刘珊珊", "会员产品", "女", "1982/10/23", "2018/5/21", ""));
        workers.add(Lists.newArrayList("楚连瑞", "会员技术", "女", "1989/10/21", "2021/9/6", ""));
        workers.add(Lists.newArrayList("郝冠亚", "专家网络", "女", "1991/11/13", "2021/9/6", ""));
        workers.add(Lists.newArrayList("赵旭阳", "咨询部门", "男", "1996/7/9", "2020/9/2", ""));
        workers.add(Lists.newArrayList("郁佳", "会员运营", "女", "1991/12/29", "2018/5/21", ""));
        workers.add(Lists.newArrayList("苏波", "会员运营", "男", "1994/5/1", "2021/3/24", ""));
        workers.add(Lists.newArrayList("祁伟", "咨询部门", "男", "1994/12/23", "2021/3/15", ""));
        workers.add(Lists.newArrayList("殷圣尧", "咨询部门", "男", "1994/6/25", "2021/5/10", ""));
        workers.add(Lists.newArrayList("王莹", "专家运营部门", "女", "1993/10/17", "2021/6/28", ""));
        workers.add(Lists.newArrayList("胡楠", "会员产品", "男", "1988/11/5", "2021/2/10", "是"));
        workers.add(Lists.newArrayList("刘浩（会员）", "会员技术", "男", "1995/8/4", "2021/8/16", ""));
        workers.add(Lists.newArrayList("王晓晨", "会员销售", "男", "1994/9/8", "2021/9/6", ""));
        workers.add(Lists.newArrayList("代雄生", "会员技术", "男", "1997/9/28", "2021/9/13", ""));
        workers.add(Lists.newArrayList("杨维山", "会员技术", "男", "1988/6/29", "2018/4/18", ""));
        workers.add(Lists.newArrayList("李佳伟", "会员技术", "男", "1995/1/31", "2021/5/12", ""));
        workers.add(Lists.newArrayList("杨坤", "会员技术", "男", "1991/5/4", "2021/5/12", ""));
        workers.add(Lists.newArrayList("杨萌", "咨询部门", "女", "1999/2/6", "2021/6/21", ""));
        workers.add(Lists.newArrayList("李敏", "专家运营部门", "女", "1986/3/19", "2021/9/8", ""));
        workers.add(Lists.newArrayList("杨诺", "会员运营", "男", "1989/8/19", "2021/9/13", ""));
        workers.add(Lists.newArrayList("袁宁宁", "咨询部门", "女", "1993/7/12", "2021/6/16", ""));
        workers.add(Lists.newArrayList("丁鹏", "会员技术", "男", "1987/3/28", "2017/12/26", ""));
        workers.add(Lists.newArrayList("朱文瑞", "会员产品", "男", "1993/5/1", "2021/5/24", ""));
        workers.add(Lists.newArrayList("闵国旭", "咨询部门", "男", "1995/2/14", "2021/3/15", ""));
        workers.add(Lists.newArrayList("张子阳", "咨询部门", "男", "1998/8/8", "2021/7/19", ""));
        workers.add(Lists.newArrayList("王点点", "会员技术", "女", "1993/2/1", "2014/7/24", ""));
        workers.add(Lists.newArrayList("马禄", "专家网络", "男", "1990/6/11", "2020/5/11", ""));
        workers.add(Lists.newArrayList("常耀中", "会员技术", "男", "1997/2/1", "2020/9/28", ""));
        workers.add(Lists.newArrayList("连美慧", "咨询部门", "女", "1994/2/8", "2021/8/4", ""));
        workers.add(Lists.newArrayList("彭子聃", "咨询部门", "男", "1997/9/12", "2020/12/16", ""));
        workers.add(Lists.newArrayList("王乙超", "会员技术", "男", "1998/10/27", "2021/9/6", ""));
        workers.add(Lists.newArrayList("王慧芳", "内容产品", "女", "1995/5/25", "2019/12/11", ""));
        workers.add(Lists.newArrayList("张正操", "内容技术", "男", "1980/1/7", "2018/3/20", "是"));
        workers.add(Lists.newArrayList("吴可", "大前端", "男", "1989/11/4", "2014/8/11", ""));
        workers.add(Lists.newArrayList("吴楚基", "内容运营", "男", "1993/9/29", "2020/8/24", ""));
        workers.add(Lists.newArrayList("冯成", "内容运营", "男", "1985/9/17", "2021/2/24", ""));
        workers.add(Lists.newArrayList("贾栋", "用户运营", "男", "1996/5/2", "2021/9/6", ""));
        workers.add(Lists.newArrayList("王继冲", "测试", "女", "1987/9/18", "2018/8/1", ""));
        workers.add(Lists.newArrayList("邓丽虹", "内容运营", "女", "1987/4/12", "2021/8/25", ""));
        workers.add(Lists.newArrayList("付祥旭", "后端", "男", "1989/4/27", "2021/2/3", ""));
        workers.add(Lists.newArrayList("胡漾", "内容运营", "男", "1979/6/8", "2021/1/11", "是"));
        workers.add(Lists.newArrayList("邹颖", "后端", "男", "1985/2/28", "2020/10/16", ""));
        workers.add(Lists.newArrayList("孙灿", "大前端", "男", "1989/7/10", "2021/5/31", ""));
        workers.add(Lists.newArrayList("王雯", "热点运营", "女", "1989/10/4", "2015/5/8", ""));
        workers.add(Lists.newArrayList("罗军", "后端", "男", "1989/9/5", "2020/9/2", ""));
        workers.add(Lists.newArrayList("熊科铭", "内容运营", "男", "1998/1/25", "2021/6/1", ""));
        workers.add(Lists.newArrayList("魏婕", "内容运营", "女", "1996/4/30", "2021/5/7", ""));
        workers.add(Lists.newArrayList("李静", "运营", "女", "1995/8/22", "2020/9/23", ""));
        workers.add(Lists.newArrayList("吉晓华", "总编室", "女", "1979/2/2", "2021/7/21", ""));
        workers.add(Lists.newArrayList("付小萌", "内容运营", "女", "1995/1/23", "2021/4/12", ""));
        workers.add(Lists.newArrayList("段南西", "大前端", "女", "1993/1/12", "2017/6/12", ""));
        workers.add(Lists.newArrayList("张文华", "后端", "男", "1987/1/5", "2019/1/7", ""));
        workers.add(Lists.newArrayList("徐鹏程", "后端", "男", "1991/4/2", "2017/10/10", ""));
        workers.add(Lists.newArrayList("佟鑫", "内容运营", "女", "1995/3/21", "2021/2/1", ""));
        workers.add(Lists.newArrayList("李雯璐", "运营", "女", "1994/3/2", "2021/8/23", ""));
        workers.add(Lists.newArrayList("李晨", "测试", "女", "1993/2/14", "2021/7/14", ""));
        workers.add(Lists.newArrayList("胡俊巍", "用户运营", "男", "1994/11/1", "2021/7/26", ""));
        workers.add(Lists.newArrayList("李琦", "内容产品", "男", "1990/2/1", "2018/7/30", ""));
        workers.add(Lists.newArrayList("吴真", "大前端", "女", "1994/1/21", "2020/8/31", ""));
        workers.add(Lists.newArrayList("吴玄青", "内容产品", "男", "1994/3/30", "2021/4/12", ""));
        workers.add(Lists.newArrayList("王彬", "内容产品", "男", "1989/6/11", "2021/9/6", ""));
        workers.add(Lists.newArrayList("王达", "内容运营", "男", "1996/9/16", "2020/8/17", ""));
        workers.add(Lists.newArrayList("牛章鹏", "内容事业部", "男", "1982/5/16", "2014/5/4", "是"));
        workers.add(Lists.newArrayList("孟令禹", "大前端", "男", "1996/3/15", "2021/4/12", ""));
        workers.add(Lists.newArrayList("喇飞虎", "后端", "男", "1995/9/28", "2020/11/25", ""));
        workers.add(Lists.newArrayList("吝如雨", "总编室", "女", "1995/3/26", "2020/11/25", ""));
        workers.add(Lists.newArrayList("付思琪", "内容运营", "女", "1998/9/26", "2021/7/1", ""));
        workers.add(Lists.newArrayList("姚城", "后端", "男", "1988/8/25", "2018/5/28", ""));
        workers.add(Lists.newArrayList("宗举", "测试", "男", "1993/11/7", "2021/9/13", ""));
        workers.add(Lists.newArrayList("刘晓兵", "内容技术", "男", "1986/11/17", "2018/10/10", ""));
        workers.add(Lists.newArrayList("安宜豪", "后端", "男", "1995/12/12", "2021/6/7", ""));
        workers.add(Lists.newArrayList("徐蔡钰", "用户运营", "女", "1998/2/27", "2021/3/1", ""));
        workers.add(Lists.newArrayList("郭明辉", "测试", "男", "1987/9/15", "2015/7/30", ""));
        workers.add(Lists.newArrayList("张梅娜", "用户运营", "女", "1993/9/25", "2018/7/2", ""));
        workers.add(Lists.newArrayList("方一苇", "内容运营", "女", "1997/9/12", "2021/7/19", ""));
        workers.add(Lists.newArrayList("王思旭", "热点运营", "女", "1997/2/15", "2021/4/19", ""));
        workers.add(Lists.newArrayList("李晴", "内容产品", "女", "1988/5/23", "2018/2/1", ""));
        workers.add(Lists.newArrayList("孙加亮", "后端", "男", "1993/5/14", "2020/8/3", ""));
        workers.add(Lists.newArrayList("刘丹", "内容运营", "女", "1998/5/19", "2021/7/1", ""));
        workers.add(Lists.newArrayList("张语嫣", "用户运营", "女", "1997/11/7", "2021/5/10", ""));
        workers.add(Lists.newArrayList("王鑫鑫", "总编室", "女", "1993/12/29", "2020/12/14", ""));
        workers.add(Lists.newArrayList("田金金", "用户运营", "女", "1993/10/8", "2021/9/15", ""));
        workers.add(Lists.newArrayList("司玮", "内容产品", "女", "1990/9/9", "2021/6/9", ""));
        workers.add(Lists.newArrayList("郭金鹏", "媒介资源", "男", "1986/2/15", "2021/8/9", ""));
        workers.add(Lists.newArrayList("张晗", "品牌推广", "女", "1996/12/5", "2021/9/8", ""));
        workers.add(Lists.newArrayList("易靖", "媒介资源", "男", "1981/10/30", "2021/8/30", ""));
        workers.add(Lists.newArrayList("谷佳俊", "公关策划", "男", "1992/1/3", "2018/7/25", ""));
        workers.add(Lists.newArrayList("柳志卿", "品牌市场中心", "男", "1980/8/3", "2021/6/28", "是"));
        workers.add(Lists.newArrayList("高博君", "品牌推广", "女", "1994/4/26", "2020/8/10", ""));
        workers.add(Lists.newArrayList("乔明", "品牌推广", "男", "1981/9/8", "2021/8/23", ""));
        workers.add(Lists.newArrayList("谢静", "公关策划", "女", "1984/10/7", "2021/8/11", ""));
        workers.add(Lists.newArrayList("许娜", "品牌推广", "女", "1984/7/13", "2020/6/10", ""));
        workers.add(Lists.newArrayList("张建", "平台后端", "男", "1986/2/21", "2017/5/25", ""));
        workers.add(Lists.newArrayList("杨逍鹏", "平台客户端", "男", "1991/12/23", "2018/8/27", ""));
        workers.add(Lists.newArrayList("张晓婉", "增长运营", "女", "1993/6/18", "2021/8/18", ""));
        workers.add(Lists.newArrayList("宋雪歌", "平台数据", "女", "1996/5/8", "2020/6/29", ""));
        workers.add(Lists.newArrayList("王越", "平台算法", "男", "1989/1/10", "2018/12/24", ""));
        workers.add(Lists.newArrayList("管文哲", "视觉设计", "男", "1990/1/12", "2018/8/29", ""));
        workers.add(Lists.newArrayList("候振东", "平台客户端", "男", "1992/2/19", "2019/4/1", ""));
        workers.add(Lists.newArrayList("周正", "平台数据", "男", "1988/11/16", "2018/11/5", ""));
        workers.add(Lists.newArrayList("韩东阳", "平台客户端", "男", "1989/9/1", "2020/4/20", ""));
        workers.add(Lists.newArrayList("秦素婕", "平台测试", "女", "1994/8/6", "2021/6/15", ""));
        workers.add(Lists.newArrayList("陈诗琪", "增长运营", "女", "1994/11/26", "2020/6/29", ""));
        workers.add(Lists.newArrayList("刘琪", "平台设计", "男", "1989/6/30", "2018/7/30", "是"));
        workers.add(Lists.newArrayList("钱鹏亮", "增长营销", "男", "1994/3/24", "2021/8/23", ""));
        workers.add(Lists.newArrayList("许翠翠", "平台数据", "女", "1989/5/16", "2021/9/6", ""));
        workers.add(Lists.newArrayList("施赛江", "平台算法", "男", "1993/1/14", "2020/7/8", ""));
        workers.add(Lists.newArrayList("谭国铭", "平台产品", "男", "1986/3/20", "2020/9/14", "是"));
        workers.add(Lists.newArrayList("赵昕琳", "平台测试", "女", "1990/4/13", "2019/3/20", ""));
        workers.add(Lists.newArrayList("莫璐", "视觉设计", "女", "1991/10/15", "2018/6/20", ""));
        workers.add(Lists.newArrayList("常文豪", "视觉设计", "男", "1995/11/18", "2018/8/29", ""));
        workers.add(Lists.newArrayList("黄笑雨", "平台数据", "女", "1998/6/28", "2021/4/26", ""));
        workers.add(Lists.newArrayList("王力", "平台效能", "男", "1989/3/8", "2020/12/16", ""));
        workers.add(Lists.newArrayList("李陈", "平台效能", "男", "1997/4/28", "2020/4/15", ""));
        workers.add(Lists.newArrayList("王珍珠", "平台算法", "女", "1991/3/5", "2020/7/13", ""));
        workers.add(Lists.newArrayList("高春娟", "平台测试", "女", "1987/2/17", "2018/5/3", ""));
        workers.add(Lists.newArrayList("贾楠", "体验设计", "女", "1983/2/21", "2016/11/17", ""));
        workers.add(Lists.newArrayList("孙嘉", "增长运营", "女", "1993/7/8", "2021/7/12", ""));
        workers.add(Lists.newArrayList("李志钢", "平台测试", "男", "1982/10/3", "2014/8/11", ""));
        workers.add(Lists.newArrayList("邹涛", "平台客户端", "男", "1995/3/30", "2018/9/26", ""));
        workers.add(Lists.newArrayList("孟广洋", "增长营销", "女", "1991/12/10", "2021/6/2", ""));
        workers.add(Lists.newArrayList("吕晓", "平台客服", "女", "1983/12/22", "2014/6/16", ""));
        workers.add(Lists.newArrayList("吴智军", "平台数据", "男", "1989/11/1", "2019/1/9", ""));
        workers.add(Lists.newArrayList("汪全", "商务合作", "男", "1995/10/20", "2021/5/14", ""));
        workers.add(Lists.newArrayList("黄文昌", "体验设计", "男", "1990/12/5", "2018/12/26", ""));
        workers.add(Lists.newArrayList("孙潇", "平台算法", "男", "1994/1/24", "2021/1/13", ""));
        workers.add(Lists.newArrayList("鲁晓宇", "平台客户端", "男", "1985/5/18", "2018/6/15", ""));
        workers.add(Lists.newArrayList("王丹", "平台数据", "女", "1992/9/10", "2020/10/28", ""));
        workers.add(Lists.newArrayList("谢万鹏", "平台算法", "男", "1986/11/28", "2018/6/27", ""));
        workers.add(Lists.newArrayList("张宇魁", "平台算法", "男", "1984/7/27", "2018/7/9", ""));
        workers.add(Lists.newArrayList("韩虎", "政府事务", "男", "1986/4/3", "2021/8/16", ""));
        workers.add(Lists.newArrayList("伍炜", "体验设计", "男", "1994/9/9", "2021/4/14", ""));
        workers.add(Lists.newArrayList("代宜琦", "体验设计", "男", "1990/10/22", "2018/8/20", ""));
        workers.add(Lists.newArrayList("惠皎", "增长技术", "女", "1997/9/3", "2021/6/24", ""));
        workers.add(Lists.newArrayList("陈林君", "体验设计", "女", "1990/4/28", "2017/11/13", ""));
        workers.add(Lists.newArrayList("刘金俭", "增长技术", "男", "1994/6/9", "2021/2/24", ""));
        workers.add(Lists.newArrayList("唐敬亚", "平台算法", "男", "1990/5/13", "2019/10/9", ""));
        workers.add(Lists.newArrayList("戴志鹏", "增长技术", "男", "1995/1/10", "2021/9/8", ""));
        workers.add(Lists.newArrayList("陶哲", "增长技术", "男", "1989/4/11", "2018/6/11", ""));
        workers.add(Lists.newArrayList("刘吉", "平台数据", "男", "1989/2/8", "2020/9/14", ""));
        workers.add(Lists.newArrayList("刘贯远", "平台运维", "男", "1990/3/15", "2020/11/2", ""));
        workers.add(Lists.newArrayList("谢文俊", "平台数据", "男", "1991/4/7", "2019/10/28", ""));
        workers.add(Lists.newArrayList("陈闯闯", "平台算法", "男", "1992/2/18", "2021/5/10", ""));
        workers.add(Lists.newArrayList("张挺", "平台技术", "男", "1982/9/27", "2018/5/28", "是"));
        workers.add(Lists.newArrayList("汪浩", "平台DA", "男", "1983/10/19", "2018/6/27", ""));
        workers.add(Lists.newArrayList("郭晓楠", "体验设计", "女", "1989/8/6", "2018/8/29", ""));
        workers.add(Lists.newArrayList("王峰", "平台算法", "男", "1989/5/2", "2019/1/17", ""));
        workers.add(Lists.newArrayList("郭慧敏", "增长营销", "女", "1997/6/5", "2021/8/13", ""));
        workers.add(Lists.newArrayList("李艳艳", "平台数据", "女", "1989/2/8", "2020/8/31", ""));
        workers.add(Lists.newArrayList("吴锡林", "平台测试", "男", "1978/12/25", "2017/9/4", ""));
        workers.add(Lists.newArrayList("苏镝", "增长技术", "女", "1996/7/17", "2021/6/17", ""));
        workers.add(Lists.newArrayList("王传雨", "体验设计", "男", "1988/11/11", "2018/7/16", ""));
        workers.add(Lists.newArrayList("李昂", "体验设计", "女", "1994/4/16", "2021/1/13", ""));
        workers.add(Lists.newArrayList("冯华志", "平台数据", "男", "1995/12/6", "2021/4/12", ""));
        workers.add(Lists.newArrayList("李泰卿", "平台测试", "男", "1989/10/13", "2020/8/24", ""));
        workers.add(Lists.newArrayList("刘云", "平台数据", "女", "1995/12/3", "2021/6/15", ""));
        workers.add(Lists.newArrayList("贾磊", "平台效能", "男", "1989/6/30", "2018/11/28", ""));
        workers.add(Lists.newArrayList("娜日松", "商务合作", "女", "1988/1/25", "2016/3/10", ""));
        workers.add(Lists.newArrayList("岳星星", "增长营销", "男", "1994/1/12", "2021/6/28", ""));
        workers.add(Lists.newArrayList("刘冬宁", "增长技术", "男", "1988/1/12", "2021/2/1", ""));
        workers.add(Lists.newArrayList("李晓玄", "平台测试", "女", "1994/12/19", "2018/12/19", ""));
        workers.add(Lists.newArrayList("韩思杰", "平台测试", "女", "1998/1/14", "2021/4/30", ""));
        workers.add(Lists.newArrayList("王立梦", "平台测试", "女", "1994/5/19", "2020/9/21", ""));
        workers.add(Lists.newArrayList("杨亚娟", "平台数据", "女", "1992/4/29", "2021/3/22", ""));
        workers.add(Lists.newArrayList("槐宗裕", "增长产品", "男", "1987/6/13", "2021/6/24", ""));
        workers.add(Lists.newArrayList("曾子昱", "增长运营", "男", "1994/1/31", "2021/3/2", ""));
        workers.add(Lists.newArrayList("代金马", "增长技术", "男", "1990/10/3", "2020/8/12", ""));
        workers.add(Lists.newArrayList("葛梦洁", "平台数据", "女", "1994/5/18", "2021/5/17", ""));
        workers.add(Lists.newArrayList("汪丽梅", "平台测试", "女", "1994/10/25", "2020/11/18", ""));
        workers.add(Lists.newArrayList("王伟", "平台数据", "男", "1985/9/18", "2019/1/9", ""));
        workers.add(Lists.newArrayList("周醒", "平台数据", "男", "1986/10/19", "2021/4/19", ""));
        workers.add(Lists.newArrayList("罗德金", "平台算法", "男", "1991/4/5", "2019/9/4", ""));
        workers.add(Lists.newArrayList("王鹏飞", "平台DA", "男", "1992/7/19", "2019/1/2", ""));
        workers.add(Lists.newArrayList("黄惠", "平台客服", "女", "1993/12/20", "2014/9/18", ""));
        workers.add(Lists.newArrayList("鄢露露", "平台效能", "女", "1991/8/3", "2021/5/7", ""));
        workers.add(Lists.newArrayList("邵明全", "平台效能", "男", "1993/8/26", "2019/7/1", ""));
        workers.add(Lists.newArrayList("翦浩", "平台算法", "男", "1985/11/6", "2018/9/12", "是"));
        workers.add(Lists.newArrayList("孟祥琰", "平台数据", "男", "1996/1/23", "2021/1/13", ""));
        workers.add(Lists.newArrayList("张博轩", "平台前端", "男", "1992/12/14", "2020/12/28", ""));
        workers.add(Lists.newArrayList("刘日", "政府事务", "女", "1985/10/27", "2018/8/15", "是"));
        workers.add(Lists.newArrayList("裴博", "政府事务", "男", "1983/9/23", "2020/2/10", ""));
        workers.add(Lists.newArrayList("韩伟", "平台算法", "男", "1988/8/6", "2018/10/24", ""));
        workers.add(Lists.newArrayList("景月辰", "增长产品", "女", "1989/4/12", "2018/1/22", ""));
        workers.add(Lists.newArrayList("杨金达", "平台后端", "男", "1987/7/2", "2017/10/10", ""));
        workers.add(Lists.newArrayList("李扬", "平台客户端", "男", "1989/6/18", "2018/8/27", ""));
        workers.add(Lists.newArrayList("蔡铭", "增长营销", "女", "1993/12/13", "2021/7/28", ""));
        workers.add(Lists.newArrayList("李长锋", "平台效能", "男", "1985/7/27", "2018/10/10", ""));
        workers.add(Lists.newArrayList("张琰", "平台测试", "男", "1995/4/25", "2020/11/23", ""));
        workers.add(Lists.newArrayList("王欢", "体验设计", "女", "1987/6/5", "2014/11/6", ""));
        workers.add(Lists.newArrayList("张廷书", "平台效能", "男", "1993/5/13", "2020/7/13", ""));
        workers.add(Lists.newArrayList("马志禹", "平台数据", "男", "1991/12/10", "2018/10/17", ""));
        workers.add(Lists.newArrayList("王嘉茹", "平台数据", "女", "1994/5/22", "2021/9/13", ""));
        workers.add(Lists.newArrayList("王松", "平台后端", "男", "1993/5/15", "2020/10/12", ""));
        workers.add(Lists.newArrayList("崔嘉轩", "社群运营", "男", "1994/1/22", "2021/9/13", ""));
        workers.add(Lists.newArrayList("常超", "增长营销", "男", "1991/4/12", "2021/3/1", ""));
        workers.add(Lists.newArrayList("汤进", "平台客服", "男", "1977/1/13", "2017/12/12", ""));
        workers.add(Lists.newArrayList("胡彦莉", "平台测试", "女", "1994/10/25", "2021/3/3", ""));
        workers.add(Lists.newArrayList("周刚", "平台测试", "男", "1986/3/12", "2017/12/7", ""));
        workers.add(Lists.newArrayList("张磊", "平台测试", "男", "1990/4/27", "2017/11/28", ""));
        workers.add(Lists.newArrayList("李英杰", "平台数据", "女", "1991/10/30", "2020/9/28", ""));
        workers.add(Lists.newArrayList("杨志龙", "平台运维", "男", "1985/6/26", "2018/7/11", ""));
        workers.add(Lists.newArrayList("王志坤", "增长产品", "男", "1988/4/4", "2012/3/8", ""));
        workers.add(Lists.newArrayList("陈广", "平台测试", "男", "1990/10/13", "2017/12/12", ""));
        workers.add(Lists.newArrayList("杨汇慧", "体验设计", "男", "1983/7/21", "2018/2/1", ""));
        workers.add(Lists.newArrayList("郝文科", "增长技术", "男", "1983/12/3", "2018/2/1", ""));
        workers.add(Lists.newArrayList("郑帆", "平台算法", "男", "1988/7/4", "2018/9/17", ""));
        workers.add(Lists.newArrayList("赵子锐", "平台设计", "男", "1998/12/2", "2020/12/14", ""));
        workers.add(Lists.newArrayList("王凯峰", "平台后端", "男", "1995/3/16", "2021/7/19", ""));
        workers.add(Lists.newArrayList("刘宏伟", "平台技术", "男", "1985/2/27", "2014/9/15", "是"));
        workers.add(Lists.newArrayList("赵一航", "平台效能", "男", "1994/5/16", "2021/6/7", ""));
        workers.add(Lists.newArrayList("张婷", "增长营销", "女", "1991/1/18", "2018/5/21", ""));
        workers.add(Lists.newArrayList("燕南", "平台运维", "男", "1984/9/11", "2019/12/16", ""));
        workers.add(Lists.newArrayList("仙争光", "增长技术", "男", "1988/11/10", "2021/9/13", ""));
        workers.add(Lists.newArrayList("李婷璐", "平台数据", "女", "1990/4/6", "2020/9/21", ""));
        workers.add(Lists.newArrayList("师鹏飞", "平台算法", "男", "1988/11/9", "2019/8/12", ""));
        workers.add(Lists.newArrayList("朱海明", "社群运营", "男", "1990/10/7", "2021/3/15", ""));
        workers.add(Lists.newArrayList("刘吟风", "增长产品", "女", "1991/8/13", "2021/6/28", ""));
        workers.add(Lists.newArrayList("邱家新", "平台算法", "男", "1991/8/6", "2018/11/26", ""));
        workers.add(Lists.newArrayList("卫帝佐", "平台运维", "男", "1988/1/14", "2020/9/23", ""));
        workers.add(Lists.newArrayList("贾亚伟", "平台算法", "男", "1989/10/12", "2019/4/22", ""));
        workers.add(Lists.newArrayList("刘树森", "天津运营", "男", "1989/2/1", "2018/5/21", ""));
        workers.add(Lists.newArrayList("张鸿志", "天津运营", "男", "1994/5/19", "2018/6/20", ""));
        workers.add(Lists.newArrayList("张晶", "天津运营", "女", "1988/7/12", "2018/7/5", ""));
        workers.add(Lists.newArrayList("郭志鹏", "深圳精英", "男", "1994/6/16", "2021/3/3", ""));
        workers.add(Lists.newArrayList("郭玮", "深圳精英", "男", "1994/3/26", "2021/4/12", ""));
        workers.add(Lists.newArrayList("侯展涛", "深圳精英", "男", "1989/9/17", "2021/9/1", ""));
        workers.add(Lists.newArrayList("罗勇", "深圳精英", "男", "1995/4/3", "2021/8/2", ""));
        workers.add(Lists.newArrayList("钱小燕", "深圳精英", "女", "1990/6/23", "2020/12/28", ""));
        workers.add(Lists.newArrayList("杨舟", "深圳精英", "女", "1992/3/28", "2021/7/5", ""));
        workers.add(Lists.newArrayList("赖培彬", "深圳KA", "男", "1994/4/29", "2021/7/5", ""));
        workers.add(Lists.newArrayList("何慧", "客户运营", "女", "1992/1/15", "2018/6/20", ""));
        workers.add(Lists.newArrayList("宋敏香", "深圳精英", "女", "1989/2/17", "2018/4/9", ""));
        workers.add(Lists.newArrayList("夏江彬", "深圳精英", "男", "1998/8/15", "2021/8/30", ""));
        workers.add(Lists.newArrayList("黄小琴", "深圳", "女", "1990/9/20", "2018/6/13", ""));
        workers.add(Lists.newArrayList("刘燕", "深圳精英", "女", "1995/6/15", "2020/4/27", ""));
        workers.add(Lists.newArrayList("陈映苏", "深圳精英", "女", "1995/8/29", "2020/8/10", ""));
        workers.add(Lists.newArrayList("田沁沁", "深圳精英", "女", "1994/2/14", "2020/11/2", ""));
        workers.add(Lists.newArrayList("李光玉", "深圳精英", "男", "1994/1/4", "2021/8/16", ""));
        workers.add(Lists.newArrayList("陈嘉婷", "深圳精英", "女", "1999/12/5", "2021/4/7", ""));
        workers.add(Lists.newArrayList("刘芷言", "深圳精英", "女", "1996/2/12", "2021/9/6", ""));
        workers.add(Lists.newArrayList("黄旭芸", "客户运营", "女", "1996/5/16", "2021/8/23", ""));
        workers.add(Lists.newArrayList("张晓", "深圳KA", "男", "1994/3/24", "2018/4/24", ""));
        workers.add(Lists.newArrayList("王腾仪", "深圳精英", "男", "1996/9/8", "2020/11/25", ""));
        workers.add(Lists.newArrayList("林雯珠", "深圳KA", "女", "1995/9/20", "2018/5/3", ""));
        workers.add(Lists.newArrayList("佘鹏辉", "深圳KA", "男", "1991/4/15", "2019/7/15", ""));
        workers.add(Lists.newArrayList("徐依", "深圳精英", "女", "1994/10/19", "2021/4/7", ""));
        workers.add(Lists.newArrayList("梁珑", "上海KA", "男", "1985/11/3", "2021/4/7", ""));
        workers.add(Lists.newArrayList("邓尧", "上海精英", "女", "1995/8/28", "2020/11/11", ""));
        workers.add(Lists.newArrayList("王亚", "上海精英", "男", "1991/7/9", "2020/11/11", ""));
        workers.add(Lists.newArrayList("聂旭磊", "上海KA", "男", "1993/6/9", "2019/11/18", ""));
        workers.add(Lists.newArrayList("聂鹤飞", "上海精英", "男", "1996/6/29", "2020/5/14", ""));
        workers.add(Lists.newArrayList("黄思雯", "上海KA", "女", "1990/2/19", "2018/2/9", ""));
        workers.add(Lists.newArrayList("王安琪", "上海精英", "女", "1994/3/5", "2020/6/3", ""));
        workers.add(Lists.newArrayList("刘俊杰", "上海KA", "男", "1993/2/5", "2021/7/19", ""));
        workers.add(Lists.newArrayList("刘鹏", "上海精英", "男", "1991/10/2", "2020/9/21", ""));
        workers.add(Lists.newArrayList("杨森", "上海KA", "男", "1991/9/6", "2019/7/10", ""));
        workers.add(Lists.newArrayList("吴海洋", "上海精英", "男", "1995/10/13", "2020/9/14", ""));
        workers.add(Lists.newArrayList("李冉", "上海精英", "男", "1998/11/11", "2021/9/13", ""));
        workers.add(Lists.newArrayList("朱晨晨", "上海KA", "女", "1991/6/4", "2018/4/17", ""));
        workers.add(Lists.newArrayList("周望璇", "上海精英", "男", "1995/12/1", "2021/6/16", ""));
        workers.add(Lists.newArrayList("席田", "上海精英", "男", "1991/10/27", "2021/2/23", ""));
        workers.add(Lists.newArrayList("宋成帅", "上海精英", "男", "1994/12/8", "2020/9/28", ""));
        workers.add(Lists.newArrayList("于绪龙", "招聘商业运营中心", "男", "1977/11/16", "2021/7/1", ""));
        workers.add(Lists.newArrayList("朱伟一", "上海精英", "男", "1997/2/16", "2020/7/29", ""));
        workers.add(Lists.newArrayList("简瑞杰", "上海精英", "男", "1995/10/21", "2021/4/21", ""));
        workers.add(Lists.newArrayList("王堃", "上海KA", "男", "1992/5/17", "2021/4/7", ""));
        workers.add(Lists.newArrayList("孙家章", "上海精英", "男", "1992/6/29", "2020/6/15", ""));
        workers.add(Lists.newArrayList("王艳辉", "上海精英", "男", "1996/4/17", "2020/8/3", ""));
        workers.add(Lists.newArrayList("王井", "上海KA", "女", "1991/1/6", "2021/8/23", ""));
        workers.add(Lists.newArrayList("刘小双", "上海KA", "女", "1991/7/2", "2020/3/1", ""));
        workers.add(Lists.newArrayList("霍张超", "上海KA", "男", "1989/9/18", "2019/9/25", ""));
        workers.add(Lists.newArrayList("罗建冰", "上海精英", "男", "1992/4/15", "2021/8/16", ""));
        workers.add(Lists.newArrayList("余佳", "上海精英", "女", "1991/1/25", "2020/7/15", ""));
        workers.add(Lists.newArrayList("张岩", "杭州精英", "男", "1991/9/18", "2021/9/13", ""));
        workers.add(Lists.newArrayList("王潘辉", "杭州KA", "女", "1991/9/6", "2020/8/3", ""));
        workers.add(Lists.newArrayList("李凡", "杭州精英", "男", "1993/12/20", "2021/8/16", ""));
        workers.add(Lists.newArrayList("朱雅婷", "杭州精英", "女", "1995/7/19", "2020/9/16", ""));
        workers.add(Lists.newArrayList("万芷雯", "杭州精英", "女", "1996/8/3", "2020/9/2", ""));
        workers.add(Lists.newArrayList("孙嬴政", "杭州KA", "女", "1994/3/29", "2020/6/15", ""));
        workers.add(Lists.newArrayList("沈彦", "杭州精英", "女", "1989/9/4", "2021/7/19", ""));
        workers.add(Lists.newArrayList("杨笛", "杭州精英", "男", "1993/11/18", "2021/3/10", ""));
        workers.add(Lists.newArrayList("李智", "上海", "男", "1989/1/23", "2019/8/7", ""));
        workers.add(Lists.newArrayList("巫涛", "杭州KA", "男", "1994/11/20", "2019/12/9", ""));
        workers.add(Lists.newArrayList("张娜", "区域运营bp组", "女", "1994/3/27", "2021/9/6", ""));
        workers.add(Lists.newArrayList("黄昇忠", "杭州精英", "男", "1993/5/15", "2021/7/19", ""));
        workers.add(Lists.newArrayList("曹乐", "杭州KA", "男", "1986/10/10", "2019/10/9", ""));
        workers.add(Lists.newArrayList("余佳（瑜伽）", "杭州精英", "女", "1995/7/8", "2020/9/9", ""));
        workers.add(Lists.newArrayList("程龙", "杭州KA", "男", "1988/12/23", "2020/7/20", ""));
        workers.add(Lists.newArrayList("卢键", "杭州KA", "男", "1985/1/28", "2019/5/22", ""));
        workers.add(Lists.newArrayList("章钒", "杭州精英", "女", "1995/3/24", "2021/6/30", ""));
        workers.add(Lists.newArrayList("徐明亮", "广州KA", "男", "1994/9/26", "2018/5/3", ""));
        workers.add(Lists.newArrayList("卓文君", "广州KA", "女", "1988/10/16", "2020/6/1", ""));
        workers.add(Lists.newArrayList("胡佳佳", "广州精英", "女", "1993/6/12", "2020/7/20", ""));
        workers.add(Lists.newArrayList("黄澜", "广州KA", "女", "1994/3/27", "2018/5/3", ""));
        workers.add(Lists.newArrayList("郑燕旋", "广州精英", "女", "1994/8/22", "2020/5/11", ""));
        workers.add(Lists.newArrayList("李思培", "广州精英", "男", "1993/12/25", "2021/3/22", ""));
        workers.add(Lists.newArrayList("蔡晓洁", "广州精英", "女", "1993/10/21", "2020/6/29", ""));
        workers.add(Lists.newArrayList("梁楚君", "广州精英", "女", "1995/8/1", "2021/8/9", ""));
        workers.add(Lists.newArrayList("梁健铭", "广州KA", "男", "1988/7/18", "2019/10/23", ""));
        workers.add(Lists.newArrayList("马力", "流程管理运营", "女", "1979/1/25", "2017/5/16", ""));
        workers.add(Lists.newArrayList("方修斌", "广州精英", "男", "1995/12/21", "2021/8/4", ""));
        workers.add(Lists.newArrayList("吴金凤", "广州精英", "女", "1995/2/25", "2021/5/7", ""));
        workers.add(Lists.newArrayList("陈香妮", "广州精英", "女", "1997/4/2", "2021/5/26", ""));
        workers.add(Lists.newArrayList("谢明倩", "广州精英", "女", "1995/8/10", "2021/4/21", ""));
        workers.add(Lists.newArrayList("苏思达", "广州精英", "女", "1994/2/20", "2021/7/28", ""));
        workers.add(Lists.newArrayList("庄庆烁", "广州精英", "男", "1996/4/27", "2020/10/28", ""));
        workers.add(Lists.newArrayList("陈波宇", "广州精英", "男", "1992/10/6", "2021/8/11", ""));
        workers.add(Lists.newArrayList("周文彬", "广州精英", "男", "1992/9/21", "2021/9/13", ""));
        workers.add(Lists.newArrayList("齐静", "广州精英", "女", "1992/3/1", "2021/8/11", ""));
        workers.add(Lists.newArrayList("鄢倩文", "广州精英", "女", "1993/7/23", "2020/6/5", ""));
        workers.add(Lists.newArrayList("胡凤娇", "广州精英", "女", "1989/12/12", "2020/11/4", ""));
        workers.add(Lists.newArrayList("贺仁强", "广州精英", "男", "1991/5/4", "2019/7/1", ""));
        workers.add(Lists.newArrayList("温裕城", "广州精英", "男", "1995/3/21", "2020/8/5", ""));
        workers.add(Lists.newArrayList("潘田娣", "广州精英", "女", "1995/2/9", "2020/8/6", ""));
        workers.add(Lists.newArrayList("熊丹", "成都", "女", "1992/6/6", "2018/7/2", ""));
        workers.add(Lists.newArrayList("陈兆阳", "招聘研发", "男", "1994/4/3", "2021/1/11", ""));
        workers.add(Lists.newArrayList("刘明星（招聘）", "北京精英二组", "女", "1990/2/8", "2021/3/24", ""));
        workers.add(Lists.newArrayList("黄志伟", "北京精英二组", "男", "1993/12/3", "2021/7/21", ""));
        workers.add(Lists.newArrayList("李欣", "北京精英二组", "女", "1993/9/22", "2021/4/7", ""));
        workers.add(Lists.newArrayList("王昆仑", "招聘研发", "男", "1997/1/2", "2021/8/25", ""));
        workers.add(Lists.newArrayList("邢兵兵", "北京精英二组", "男", "1998/4/3", "2021/3/10", ""));
        workers.add(Lists.newArrayList("王淼", "北京精英一组", "女", "1993/8/25", "2021/3/1", ""));
        workers.add(Lists.newArrayList("王宇", "招聘研发", "女", "1993/11/24", "2019/10/10", ""));
        workers.add(Lists.newArrayList("郎雅婷", "B profile", "女", "1992/12/4", "2020/9/23", ""));
        workers.add(Lists.newArrayList("梁潮", "商业策略", "女", "1994/10/20", "2020/12/18", ""));
        workers.add(Lists.newArrayList("化丽君", "北京KA一组", "女", "1992/10/21", "2021/9/1", ""));
        workers.add(Lists.newArrayList("李东博", "北京KA二组", "男", "1987/4/14", "2020/4/27", ""));
        workers.add(Lists.newArrayList("史丰瑞", "北京KA二组", "男", "1990/7/26", "2021/8/30", ""));
        workers.add(Lists.newArrayList("陈学帅", "流程管理运营", "女", "1995/3/3", "2021/8/23", ""));
        workers.add(Lists.newArrayList("陈晓珊", "招聘研发", "女", "1993/3/30", "2021/9/6", ""));
        workers.add(Lists.newArrayList("任君馨", "北京", "男", "1987/2/9", "2021/9/13", ""));
        workers.add(Lists.newArrayList("刘全", "北京KA一组", "男", "1989/1/15", "2018/1/18", ""));
        workers.add(Lists.newArrayList("王永飞", "B profile", "女", "1993/3/3", "2021/6/30", ""));
        workers.add(Lists.newArrayList("李博", "北京KA二组", "女", "1994/4/2", "2018/7/23", ""));
        workers.add(Lists.newArrayList("虢鹏", "招聘产品", "男", "1989/7/3", "2014/8/20", ""));
        workers.add(Lists.newArrayList("郭欣", "招聘研发", "女", "1995/2/14", "2019/2/13", ""));
        workers.add(Lists.newArrayList("王倩", "招聘业务事业部", "女", "1982/4/26", "2014/9/9", "是"));
        workers.add(Lists.newArrayList("申啸天", "招聘研发", "男", "1996/8/18", "2021/3/3", ""));
        workers.add(Lists.newArrayList("王雪", "流程管理运营", "女", "1986/7/23", "2020/10/19", ""));
        workers.add(Lists.newArrayList("刘敏", "招聘研发", "女", "1993/1/15", "2020/1/9", ""));
        workers.add(Lists.newArrayList("钟浩天", "销售运营", "男", "1988/5/21", "2020/11/11", ""));
        workers.add(Lists.newArrayList("罗瑶瑶", "北京精英二组", "男", "1991/12/18", "2021/9/6", ""));
        workers.add(Lists.newArrayList("李刚", "招聘研发", "男", "1986/2/4", "2017/11/16", ""));
        workers.add(Lists.newArrayList("张皓粼", "招聘产品", "男", "1997/2/25", "2021/2/4", ""));
        workers.add(Lists.newArrayList("李广昊", "北京KA一组", "男", "1984/3/21", "2017/10/24", ""));
        workers.add(Lists.newArrayList("管春琪", "招聘产品", "男", "1992/8/16", "2020/8/10", ""));
        workers.add(Lists.newArrayList("王鹏", "北京KA一组", "男", "1987/11/3", "2018/4/10", ""));
        workers.add(Lists.newArrayList("边永朔", "北京精英一组", "男", "1996/10/29", "2021/3/31", ""));
        workers.add(Lists.newArrayList("张庆富", "北京KA一组", "男", "1990/8/26", "2018/4/17", ""));
        workers.add(Lists.newArrayList("李芳芳", "北京精英一组", "女", "1987/5/15", "2020/8/3", ""));
        workers.add(Lists.newArrayList("吴少云", "招聘研发", "男", "1989/9/28", "2018/5/21", ""));
        workers.add(Lists.newArrayList("苗静", "B profile", "女", "1990/5/15", "2017/6/8", ""));
        workers.add(Lists.newArrayList("简琦", "招聘研发", "保密", "1996/8/2", "2020/6/15", ""));
        workers.add(Lists.newArrayList("刘月", "北京KA一组", "女", "1989/9/10", "2021/5/10", ""));
        workers.add(Lists.newArrayList("李洪波", "区域运营bp组", "女", "1993/4/28", "2021/8/25", ""));
        workers.add(Lists.newArrayList("方涛", "B profile", "男", "1993/10/24", "2020/10/19", ""));
        workers.add(Lists.newArrayList("孙庆忠", "北京精英一组", "男", "1992/8/23", "2018/4/11", ""));
        workers.add(Lists.newArrayList("李欣欣", "北京精英二组", "女", "1995/8/24", "2021/9/8", ""));
        workers.add(Lists.newArrayList("李子桐", "北京KA二组", "男", "1993/2/10", "2021/9/6", ""));
        workers.add(Lists.newArrayList("杜胜南", "北京KA一组", "女", "1985/4/6", "2021/3/24", ""));
        workers.add(Lists.newArrayList("王雷蕾", "客户运营", "女", "1992/1/10", "2018/4/9", ""));
        workers.add(Lists.newArrayList("徐琼", "客户运营", "女", "1995/8/1", "2021/8/16", ""));
        workers.add(Lists.newArrayList("于宁", "招聘研发", "男", "1994/4/19", "2020/8/12", ""));
        workers.add(Lists.newArrayList("朱雪雷", "商业策略", "男", "1991/12/2", "2021/4/26", ""));
        workers.add(Lists.newArrayList("马丽萍", "招聘研发", "女", "1994/11/12", "2020/7/20", ""));
        workers.add(Lists.newArrayList("杜琳", "B profile", "女", "1994/7/18", "2021/7/14", ""));
        workers.add(Lists.newArrayList("李贵媛", "招聘研发", "女", "1995/4/13", "2020/11/4", ""));
        workers.add(Lists.newArrayList("谷凯月", "招聘产品", "女", "1993/8/15", "2021/6/16", ""));
        workers.add(Lists.newArrayList("米磊", "北京精英一组", "男", "1995/2/21", "2021/4/12", ""));
        workers.add(Lists.newArrayList("赵文元", "招聘市场", "男", "1985/10/25", "2018/4/24", ""));
        workers.add(Lists.newArrayList("钟磊", "招聘产品", "男", "1981/9/8", "2020/8/6", "是"));
        workers.add(Lists.newArrayList("刘威", "招聘市场", "女", "1989/5/6", "2020/7/1", ""));
        workers.add(Lists.newArrayList("胡月", "流程管理运营", "女", "1993/7/27", "2019/4/29", ""));
        workers.add(Lists.newArrayList("刘倩一", "招聘产品", "女", "1991/4/19", "2019/5/20", ""));
        workers.add(Lists.newArrayList("韩孝冰", "招聘研发", "男", "1983/11/1", "2013/8/26", "是"));
        workers.add(Lists.newArrayList("彭俊浩", "招聘研发", "男", "1996/3/27", "2020/10/12", ""));
        workers.add(Lists.newArrayList("王跃", "B profile", "男", "1991/10/15", "2021/4/26", ""));
        workers.add(Lists.newArrayList("徐京薇", "招聘研发", "女", "1993/5/20", "2020/6/8", ""));
        workers.add(Lists.newArrayList("马征", "北京KA二组", "女", "1993/5/4", "2021/9/6", ""));
        workers.add(Lists.newArrayList("胥言", "销售运营", "女", "1998/12/28", "2021/7/1", ""));
        workers.add(Lists.newArrayList("崔雪雪", "培训运营", "女", "1992/1/12", "2021/3/24", ""));
        workers.add(Lists.newArrayList("马骉", "北京KA二组", "男", "1990/3/4", "2018/3/14", ""));
        workers.add(Lists.newArrayList("白露", "招聘产品", "女", "1994/1/30", "2020/7/13", ""));
        workers.add(Lists.newArrayList("张兵杰", "北京精英一组", "女", "1992/2/14", "2020/9/14", ""));
        workers.add(Lists.newArrayList("于锐", "招聘研发", "男", "1991/1/3", "2020/11/25", ""));
        workers.add(Lists.newArrayList("李东", "招聘研发", "男", "1983/12/27", "2018/6/6", ""));
        workers.add(Lists.newArrayList("王侠君", "招聘市场", "女", "1984/8/26", "2018/7/23", ""));
        workers.add(Lists.newArrayList("刘浩（招聘）", "招聘研发", "男", "1991/7/13", "2019/10/24", ""));
        workers.add(Lists.newArrayList("林正琴", "北京精英一组", "女", "1993/9/4", "2020/6/1", ""));
        workers.add(Lists.newArrayList("唐振君", "招聘研发", "女", "1989/12/6", "2019/11/13", ""));
        workers.add(Lists.newArrayList("张汐蔓", "商业策略", "女", "1992/8/19", "2021/4/19", ""));
        workers.add(Lists.newArrayList("孙烨", "北京精英二组", "女", "1993/5/30", "2020/11/9", ""));
        workers.add(Lists.newArrayList("陈晨", "华北", "男", "1988/3/25", "2018/1/25", ""));
        workers.add(Lists.newArrayList("冀佟伟", "B profile", "男", "1994/6/23", "2021/8/30", ""));
        workers.add(Lists.newArrayList("汪燃", "招聘产品", "女", "1989/11/25", "2021/4/12", ""));
        workers.add(Lists.newArrayList("刘艳凤", "北京KA一组", "女", "1994/3/24", "2021/1/4", ""));
        workers.add(Lists.newArrayList("李俊逸", "招聘研发", "男", "1995/11/15", "2021/4/28", ""));
        workers.add(Lists.newArrayList("李羽", "北京精英一组", "女", "1986/5/31", "2020/7/13", ""));
        workers.add(Lists.newArrayList("黄福全", "华南", "男", "1982/2/22", "2018/4/2", ""));
        workers.add(Lists.newArrayList("贾滨婴", "北京精英一组", "女", "1997/7/9", "2020/5/13", ""));
        workers.add(Lists.newArrayList("刘群智", "招聘产品", "男", "1995/4/1", "2021/4/14", ""));
        workers.add(Lists.newArrayList("孟唱唱", "北京KA二组", "女", "1989/8/30", "2019/7/31", ""));
        workers.add(Lists.newArrayList("赵忠芳", "北京精英一组", "女", "1988/5/25", "2020/8/24", ""));
        workers.add(Lists.newArrayList("万佳东", "招聘研发", "男", "1996/1/1", "2018/1/11", ""));
        workers.add(Lists.newArrayList("张琦（招聘）", "B profile", "男", "1994/3/18", "2021/3/29", ""));
        workers.add(Lists.newArrayList("单伟强", "B profile", "男", "1992/10/9", "2021/8/11", ""));
        workers.add(Lists.newArrayList("范新旗", "招聘研发", "男", "1988/11/28", "2018/9/12", ""));
        workers.add(Lists.newArrayList("单赟吉", "招聘研发", "男", "1993/5/25", "2021/6/23", ""));
        workers.add(Lists.newArrayList("甄翊琦", "B profile", "女", "1990/4/26", "2015/8/24", ""));
        workers.add(Lists.newArrayList("付海丰", "招聘业务事业部", "男", "1984/7/16", "2020/4/14", "是"));
        workers.add(Lists.newArrayList("谭畅", "招聘市场", "女", "1985/4/9", "2020/11/9", ""));
        workers.add(Lists.newArrayList("侯冲冲", "B profile", "女", "1993/5/4", "2020/9/28", ""));
        workers.add(Lists.newArrayList("文洋洋", "北京精英一组", "男", "1992/10/7", "2021/8/23", ""));
        workers.add(Lists.newArrayList("张舜", "北京精英一组", "男", "1989/9/7", "2021/8/23", ""));
        workers.add(Lists.newArrayList("李宁", "北京精英一组", "男", "1988/5/22", "2021/5/24", ""));
        workers.add(Lists.newArrayList("郝虹阳", "招聘研发", "女", "1996/1/19", "2021/7/28", ""));
        workers.add(Lists.newArrayList("郑鑫", "北京精英二组", "男", "1990/3/20", "2021/8/20", ""));
        workers.add(Lists.newArrayList("马恒洋", "北京精英二组", "男", "1995/2/9", "2020/10/16", ""));
        workers.add(Lists.newArrayList("邹志颖", "招聘研发", "男", "1994/5/11", "2019/10/23", ""));
        workers.add(Lists.newArrayList("田映雪", "北京精英二组", "女", "1989/6/16", "2021/5/10", ""));
        workers.add(Lists.newArrayList("杨博涵", "北京KA二组", "女", "1990/1/13", "2018/2/6", ""));
        workers.add(Lists.newArrayList("侯传斌", "招聘市场", "男", "1990/10/3", "2021/9/13", ""));
        workers.add(Lists.newArrayList("段明", "北京精英一组", "女", "1994/3/15", "2020/8/5", ""));
        workers.add(Lists.newArrayList("张浩", "北京KA二组", "男", "1989/9/3", "2021/4/26", ""));
        workers.add(Lists.newArrayList("祁晔", "招聘市场", "女", "1993/2/23", "2021/3/8", ""));
        workers.add(Lists.newArrayList("王霄", "招聘产品", "男", "1999/12/18", "2021/4/12", ""));
        workers.add(Lists.newArrayList("杨滢", "商业策略", "女", "1990/4/23", "2020/7/27", ""));
        workers.add(Lists.newArrayList("袁长春", "行政", "男", "1977/3/3", "2018/4/17", ""));
        workers.add(Lists.newArrayList("路泽明", "投资", "男", "1992/3/29", "2021/6/16", ""));
        workers.add(Lists.newArrayList("陈茂林", "核算&税务", "女", "1994/5/5", "2017/7/1", "是"));
        workers.add(Lists.newArrayList("吴俊龙", "HR", "男", "1988/2/8", "2019/11/20", "是"));
        workers.add(Lists.newArrayList("关锦怡", "HR", "女", "1993/5/15", "2021/8/4", "是"));
        workers.add(Lists.newArrayList("温文", "法务", "女", "1992/2/3", "2018/8/1", ""));
        workers.add(Lists.newArrayList("袁也", "BP&资金", "女", "1994/12/7", "2020/11/30", "是"));
        workers.add(Lists.newArrayList("孙文雅", "BP&资金", "女", "1991/4/15", "2014/9/15", "是"));
        workers.add(Lists.newArrayList("李仁才", "HR", "男", "1988/6/5", "2021/7/14", "是"));
        workers.add(Lists.newArrayList("王晓彤", "HR", "女", "1995/1/13", "2021/4/14", "是"));
        workers.add(Lists.newArrayList("周巍巍", "HR", "女", "1989/12/20", "2019/9/23", "是"));
        workers.add(Lists.newArrayList("蔡展", "HR", "女", "1986/5/1", "2018/5/8", "是"));
        workers.add(Lists.newArrayList("周深远", "HR", "女", "1993/6/15", "2021/7/9", "是"));
        workers.add(Lists.newArrayList("刘婷婷", "HR", "女", "1987/6/21", "2019/6/3", "是"));
        workers.add(Lists.newArrayList("汪祺", "行政", "女", "1994/8/12", "2021/8/11", ""));
        workers.add(Lists.newArrayList("张艳卿", "核算&税务", "女", "1990/1/29", "2020/5/25", "是"));
        workers.add(Lists.newArrayList("谢铠鸿", "法务", "男", "1994/11/23", "2021/3/29", ""));
        workers.add(Lists.newArrayList("冯岩", "投资", "女", "1988/11/20", "2021/4/7", ""));
        workers.add(Lists.newArrayList("李晓贺", "核算&税务", "女", "1986/10/26", "2020/10/28", "是"));
        workers.add(Lists.newArrayList("翁智明", "投资", "男", "1986/3/12", "2018/5/28", ""));
        workers.add(Lists.newArrayList("韩晓", "HR", "男", "1986/10/21", "2020/9/21", "是"));
        workers.add(Lists.newArrayList("陈子璐", "投资", "男", "1991/8/26", "2021/3/22", ""));
        workers.add(Lists.newArrayList("李梦", "法务", "女", "1984/2/12", "2021/4/26", ""));
        workers.add(Lists.newArrayList("刘亚坤", "核算&税务", "女", "1984/11/1", "2018/6/20", "是"));
        workers.add(Lists.newArrayList("宋晓颖", "HR", "女", "1993/5/25", "2020/8/17", "是"));
        workers.add(Lists.newArrayList("徐慧中", "HR", "女", "1993/1/21", "2021/7/26", "是"));
        workers.add(Lists.newArrayList("鞠建利", "BP&资金", "男", "1986/2/4", "2020/4/20", "是"));
        workers.add(Lists.newArrayList("高晶晶", "BP&资金", "女", "1995/7/4", "2021/2/22", "是"));
        workers.add(Lists.newArrayList("周烨", "行政", "男", "1986/1/13", "2020/5/20", ""));
        workers.add(Lists.newArrayList("程萍", "CEO办公室", "女", "1987/7/8", "2019/3/27", "是"));
        workers.add(Lists.newArrayList("梁甜甜", "行政", "女", "1998/5/10", "2020/8/1", ""));
        workers.add(Lists.newArrayList("邹佳宇", "HR", "女", "1993/4/14", "2019/3/20", "是"));
        workers.add(Lists.newArrayList("毛雨婷", "行政", "女", "1999/6/21", "2021/6/15", ""));
        workers.add(Lists.newArrayList("王晗", "HR", "男", "1995/11/23", "2021/2/1", "是"));
        workers.add(Lists.newArrayList("罗云朋", "HR", "男", "1984/1/28", "2020/5/11", "是"));
        workers.add(Lists.newArrayList("赵安", "HR", "女", "1996/5/31", "2021/8/9", "是"));
        workers.add(Lists.newArrayList("李韩春", "采购", "女", "1992/10/17", "2017/11/9", ""));
        workers.add(Lists.newArrayList("王子萱", "HR", "女", "1999/2/25", "2021/7/1", "是"));
        workers.add(Lists.newArrayList("侯晓婷", "HR", "女", "1989/9/20", "2021/8/25", "是"));
        workers.add(Lists.newArrayList("宋爱杰", "BP&资金", "女", "1987/9/19", "2018/11/14", "是"));
        workers.add(Lists.newArrayList("杨占飞", "采购", "男", "1988/3/16", "2019/3/15", ""));
        workers.add(Lists.newArrayList("赖宏婕", "财务", "女", "1981/9/29", "2012/3/8", "是"));
        workers.add(Lists.newArrayList("苏梦晓", "HR", "女", "1991/5/23", "2018/7/11", "是"));
        workers.add(Lists.newArrayList("肖姗", "HR", "女", "1997/3/18", "2020/7/15", "是"));
        workers.add(Lists.newArrayList("张雅坤", "投资", "女", "1995/11/15", "2021/7/28", ""));
        workers.add(Lists.newArrayList("刘静", "深圳KA", "女", "1992/5/2", "2021/9/16", ""));
        workers.add(Lists.newArrayList("李佳明", "深圳精英", "男", "1995/3/3", "2021/9/16", ""));
        workers.add(Lists.newArrayList("于挺", "政府事务", "男", "1986/6/29", "2021/9/16", ""));
        return workers;
    }
}
