export class Tool {
  /**
   * 空校验 null或""都返回true
   */
  public static isEmpty (obj: any) {
    if ((typeof obj === 'string')) {
      return !obj || obj.replace(/\s+/g, "") === ""
    } else {
      return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0);
    }
  }

  /**
   * 非空校验
   */
  public static isNotEmpty (obj: any) {
    return !this.isEmpty(obj);
  }

  /**
   * 对象复制
   * @param obj
   */
  public static copy (obj: object) {
    if (Tool.isNotEmpty(obj)) {
      return JSON.parse(JSON.stringify(obj));
    }
  }

  /**
   * 使用递归将数组转为tree树形结构
   * 父ID属性为parent
   */

  //第一个参数array就是对应的一个数组，第二个参数对应的父ID，从array数组里吧parent=parentId的全部拿出来
  public static array2Tree (array: any, parentId: number) {
    if (Tool.isEmpty(array)) {
      return [];
    }

    const result = [];

    //因为是一个数组，因此会循环
    for (let i = 0; i < array.length; i++) {
      const c = array[i];
      // console.log(Number(c.parent), Number(parentId));
      //单次功能：从数组array中，将父ID是parentID的找出来
      //当判断循环的数据，它的parent=传进来0（第一层）
      if (Number(c.parent) === Number(parentId)) {
        //把result push进去，表示找到了此条数据，拿到慈航数据后，还要对此行数据再做一个递归，此方法再调用一次
        result.push(c);

        // 递归查看当前节点对应的子节点
        const children = Tool.array2Tree(array, c.id);
        if (Tool.isNotEmpty(children)) {
          c.children = children;
        }
      }
    }
    //返回result
    return result;
  }

  /**
   * 随机生成[len]长度的[radix]进制数
   * @param len
   * @param radix 默认62
   * @returns {string}
   */
  public static uuid (len: number, radix = 62) {
    const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    const uuid = [];
    radix = radix || chars.length;

    for (let i = 0; i < len; i++) {
      uuid[i] = chars[0 | Math.random() * radix];
    }

    return uuid.join('');
  }
}
