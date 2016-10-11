package hibernate.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "COMPONENT")
public class Component implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0 NOT NULL")
    private int quantity = 1;

    @Override
    public int hashCode()
    {
        return Objects.hash(item, name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }

        if(obj == null || !(obj instanceof Component))
        {
            return false;
        }

        Component other = (Component) obj;

        return Objects.equals(item, other.getItem()) && Objects.equals(name, other.getName());
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + ": id=[" + id + "] item=[" + item + "] name=[" + name + "]";
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}